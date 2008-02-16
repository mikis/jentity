package org.dataentity.numberbean;

import javax.swing.JSlider;

import org.dataentity.datamodel.ChangeEventConstraint;
import org.dataentity.datamodel.ChangeListener;
import org.dataentity.datamodel.DataEntity;
import org.dataentity.datamodel.ParameterEnum;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

public class SliderBeanTest extends MockObjectTestCase {
	private  SliderBeanModel model;
	private  JSlider slider;
	protected SliderBean bean;
	private Mock mockListener; 

	protected void setUp() throws Exception {
		super.setUp();

		model = new SliderBeanModel();
		// Initialize model
		model.setAttribute(SliderBeanModel.MIN, new Integer(0));
		model.setAttribute(SliderBeanModel.MAX, new Integer(1000));
		model.setAttribute(SliderBeanModel.VALUE, new Integer(500));
		
		slider =new JSlider();
		bean = new SliderBean(slider,model, SliderBeanModel.VALUE,SliderBeanModel.MIN, SliderBeanModel.MAX);
		bean.attachToModel();
		mockListener = mock(ChangeListener.class);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.addListener((ChangeListener) mockListener.proxy());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * User update of the min attribute should cause a update of the min attribute in the model.
	 */
	public void testUserEventMin() {
		Integer min = new Integer(100);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.MIN, min);
		mockListener.expects(once()).method("handleUpdate").with( changeEventUpdateEq(expectedUpdate) );
		slider.setMinimum(min.intValue());
	}
	
	public void testUserEventMax() {
		Integer max = new Integer(900);
		SliderBeanModel expectedUpdate = new SliderBeanModel();
		expectedUpdate.setAttribute(SliderBeanModel.MAX, max);        
		mockListener.expects(once()).method("handleUpdate").with( changeEventUpdateEq(expectedUpdate) );
		slider.setMaximum(max.intValue());
	}
	
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
	public void testModelEventMin() {
		Integer min = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.MIN, min);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.update(update);
		assertEquals("Wrong slider min attribute after update", min.intValue(), slider.getMinimum());
	}
	
	public void testModelEventMax() {
		Integer max = new Integer(100);
		SliderBeanModel update = new SliderBeanModel();
		update.setAttribute(SliderBeanModel.MAX, max);
		mockListener.expects(once()).method("handleUpdate").with(ANYTHING  );
		model.update(update);
		assertEquals("Wrong slider max attribute after update", max.intValue(), slider.getMaximum());
	}
	
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
	}
	
	public static class SliderBeanModelParameter extends ParameterEnum {
        public SliderBeanModelParameter(String name) {
          	super(name);
        }
    }
}