package org.jboss.logging.util;

import org.jboss.logging.Message;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Pollet
 */
public final class ElementUtil {

     private ElementUtil() {

     }


    public static Collection<ExecutableElement> getAllMethodsOfInterface(final TypeElement element, final Types types) {
        if (!element.getKind().isInterface()) {
            throw new IllegalArgumentException("The element parameter is not an interface");
        }

        Collection<ExecutableElement> methods = new HashSet<ExecutableElement>();

        for (TypeMirror intf : element.getInterfaces()) {
            methods.addAll(getAllMethodsOfInterface((TypeElement) types.asElement(intf), types));
        }

        methods.addAll(ElementFilter.methodsIn(element.getEnclosedElements()));

       return methods;
    }

    public static Map<String, String> getAllMethodsMessage(final Collection<ExecutableElement> methods) {

        Map<String, String> messages = new HashMap<String, String>();

        for (ExecutableElement method : methods) {
            Message annotation = method.getAnnotation(Message.class);
            if (annotation != null) {
                messages.put(method.getSimpleName().toString(), annotation.value());
            }
        }

        return messages;
    }


}
