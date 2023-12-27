package cn.xphsc.web.common.lang.define;

public class CloneSupport<T> implements Cloneable {

    @SuppressWarnings("unchecked")
    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}