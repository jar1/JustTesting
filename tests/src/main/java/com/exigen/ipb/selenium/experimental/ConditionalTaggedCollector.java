package com.exigen.ipb.selenium.experimental;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collects members of container that conforms to specified condition.
 * 
 * @author gzukas
 * @since 3.9
 *
 * @param <T>  The type of container.
 * @param <K>  The type of member.
 */
public class ConditionalTaggedCollector<T, K> extends TaggedCollector<T, K> {

    private Condition<K> condition;

    public ConditionalTaggedCollector(Class<K> memberClass) {
    	super(memberClass);
	}
    
    public ConditionalTaggedCollector(Class<K> memberClass, Condition<K> condition) {
        this(memberClass);
        this.condition = condition;
    }

    public ConditionalTaggedCollector(Class<K> memberClass, Condition<K> condition, String tagName) {
        this(memberClass, condition);
        this.tagName = tagName;
    }

    @Override
    public Collection<K> extract(T container) {
        Collection<K> members = new ArrayList<K>();

        for (K member : super.extract(container)) {
            if (condition.apply(member)) {
                members.add(member);
            }
        }

        return members;
    }
    
    public Condition<K> getCondition() {
		return condition;
	}
    
    public void setCondition(Condition<K> condition) {
		this.condition = condition;
	}

}
        