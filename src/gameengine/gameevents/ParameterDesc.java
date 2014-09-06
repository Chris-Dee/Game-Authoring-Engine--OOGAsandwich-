package gameengine.gameevents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterDesc {
	String name();
	String description();
	Class<?> type();
}
