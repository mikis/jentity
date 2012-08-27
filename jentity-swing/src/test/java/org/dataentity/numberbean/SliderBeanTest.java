package org.dataentity.numberbean;

import org.jentity.datamodel.ChangeEventMatcher;
import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.ParameterEnum;
import org.jentity.numberbean.SliderBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;

import static org.mockito.Mockito.*;

public class SliderBeanTest {
	private  SliderBeanModel model;
	private  JSlider slider;
	protected SliderBean bean;
    private ChangeListener changeListener;

    @BeforeMethod
	protected void setUp() throws Exception {
		model = new SliderBeanModel();
		// Initialize model
		model.setAttribute(SliderBeanModel.MIN, new Integer(0));
		model.setAttribute(SliderBeanModel.MAX, new Integer(1000));
		model.setAttribute(SliderBeanModel.VALUE, new Integer(500));
		
		slider = new JSlider();
		bean = new SliderBean(slider, model, SliderBeanModel.VALUE, SliderBeanModel.MIN, SliderBeanModel.MAX);
		bean.attachToModel();
        changeListener = mock(ChangeListener.class);
		model.addListener(changeListener);
        verify(changeListener).handleUpdate((ChangeListener.ChangeEvent) anyObject());
        reset(changeListener);
	}

	/**
	 * User update of the min attribute should cause a update of the min attribute in the model.
	 */
    @Test
	public void testUserEventMin() {
		Integer min = new Integer(100);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.MIN, min);
		slider.setMinimum(min);
        verify(changeListener).handleUpdate(changeEventUpdateEq(expectedUpdate));
	}

    @Test
	public void testUserEventMax() {
		Integer max = new Integer(900);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.MAX, max);
		slider.setMaximum(max.intValue());
        verify(changeListener).handleUpdate(changeEventUpdateEq(expectedUpdate));
	}

    @Test
	public void testUserEventValue() {
		Integer value = new Integer(900);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.VALUE, value);
		slider.setValue(value.intValue());
        verify(changeListener).handleUpdate(changeEventUpdateEq(expectedUpdate));
	}
	
	/**
	 * Update of the models min attribute should cause a update of the JSliders min attribute. 
	 */
    @Test
	public void testModelEventMin() {
		Integer min = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.MIN, min);
		model.update(update);
		Assert.assertEquals(slider.getMinimum(), min.intValue(), "Wrong slider min attribute after update");
	}

    @Test
	public void testModelEventMax() {
		Integer max = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.MAX, max);
		model.update(update);
		Assert.assertEquals(slider.getMaximum(), max.intValue(), "Wrong slider max attribute after update");
	}

    @Test
	public void testModelEventValue() {
		Integer value = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.VALUE, value);
		model.update(update);
		Assert.assertEquals(slider.getValue(), value.intValue(), "Wrong slider value attribute after update");
	}
	
	/**
	 * Update the model. The Bean will be notified of this and will be update, but should not generate another update.
	 */
    @Test
	public void testEventLoopModelEvent() {
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.VALUE, new Integer(900));
		model.update(update);
        verify(changeListener).handleUpdate((ChangeListener.ChangeEvent) anyObject());
	}

	private ChangeListener.ChangeEvent changeEventUpdateEq( DataEntity update ) {
        return argThat(new ChangeEventMatcher(update));
    }
	
	static class SliderBeanModel extends DataEntity {
	  	public static final SliderBeanModelParameter VALUE = new SliderBeanModelParameter("value");
	  	public static final SliderBeanModelParameter MIN = new SliderBeanModelParameter("min");
	  	public static final SliderBeanModelParameter MAX = new SliderBeanModelParameter("max");

		public DataEntity createInstance() {
			return new SliderBeanModel();
		}

		@Override
		public Class getParameterEnumClass() {
			return SliderBeanModelParameter.class;
		}		
	}
	
	public static class SliderBeanModelParameter extends ParameterEnum {
        public SliderBeanModelParameter(String name) {
          	super(name);
        }
    }
}
