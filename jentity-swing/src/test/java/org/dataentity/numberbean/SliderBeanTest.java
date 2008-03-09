package org.dataentity.numberbean;

import javax.swing.JSlider;

import org.jentity.datamodel.ChangeEventConstraint;
import org.jentity.datamodel.ChangeListener;
import org.jentity.datamodel.DataEntity;
import org.jentity.datamodel.ParameterEnum;
import org.jentity.numberbean.SliderBean;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SliderBeanTest extends MockObjectTestCase {
	private  SliderBeanModel model;
	private  JSlider slider;
	protected SliderBean bean;
	private Mock mockListener; 

    @BeforeTest
	protected void setUp() throws Exception {
    	super.setUp();
		model = new SliderBeanModel();
		// Initialize model
		model.setAttribute(SliderBeanModel.MIN, new Integer(0));
		model.setAttribute(SliderBeanModel.MAX, new Integer(1000));
		model.setAttribute(SliderBeanModel.VALUE, new Integer(500));
		
		slider = new JSlider();
		bean = new SliderBean(slider,model, SliderBeanModel.VALUE,SliderBeanModel.MIN, SliderBeanModel.MAX);
		bean.attachToModel();
		mockListener = mock(ChangeListener.class);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.addListener((ChangeListener) mockListener.proxy());
	}

    @AfterTest
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * User update of the min attribute should cause a update of the min attribute in the model.
	 */
    @Test
	public void testUserEventMin() {
		Integer min = new Integer(100);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.MIN, min);
		mockListener.expects(once()).method("handleUpdate").with( changeEventUpdateEq(expectedUpdate) );
		slider.setMinimum(min.intValue());
	}

    @Test
	public void testUserEventMax() {
		Integer max = new Integer(900);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.MAX, max);        
		mockListener.expects(once()).method("handleUpdate").with( changeEventUpdateEq(expectedUpdate) );
		slider.setMaximum(max.intValue());
	}

    @Test
	public void testUserEventValue() {
		Integer value = new Integer(900);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.VALUE, value);        
		mockListener.expects(once()).method("handleUpdate").with( changeEventUpdateEq(expectedUpdate) );
		slider.setValue(value.intValue());
	}
	
	/**
	 * Update of the models min attribute should cause a update of the JSliders min attribute. 
	 */
    @Test
	public void testModelEventMin() {
		Integer min = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.MIN, min);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.update(update);
		assertEquals("Wrong slider min attribute after update", min.intValue(), slider.getMinimum());
	}

    @Test
	public void testModelEventMax() {
		Integer max = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.MAX, max);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.update(update);
		assertEquals("Wrong slider max attribute after update", max.intValue(), slider.getMaximum());
	}

    @Test
	public void testModelEventValue() {
		Integer value = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.VALUE, value);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.update(update);
		assertEquals("Wrong slider value attribute after update", value.intValue(), slider.getValue());
	}
	
	/**
	 * Update the model. The Bean will be notified of this and will be update, but should not generate another update.
	 */
    @Test
	public void testEventLoopModelEvent() {
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.VALUE, new Integer(900));   
		mockListener.expects(once()).method("handleUpdate").with( ANYTHING );
		model.update(update);
	}

	private Constraint changeEventUpdateEq( DataEntity update ) {
        return new ChangeEventConstraint(update);
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
