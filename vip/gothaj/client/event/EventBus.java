package vip.gothaj.client.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import vip.gothaj.client.Client;
import vip.gothaj.client.event.events.EventRender3D;
import vip.gothaj.client.event.events.EventUpdate;
import vip.gothaj.client.utils.shader.ShaderRenderer.ShaderType;

public class EventBus {
	
	private HashMap<Class, List<EventInfo>> listeners = new HashMap<Class, List<EventInfo>>();

	public void register(Object module) {
		for(Method m : module.getClass().getDeclaredMethods()) {
			if(!m.isAnnotationPresent(EventListener.class) || m.getParameterTypes().length != 1 || m.getDeclaredAnnotations().length > 1) continue;

			List<EventInfo> infos = listeners.get(m.getParameterTypes()[0]);
			
			if(infos == null) {

				List<EventInfo> methods = new ArrayList();
				methods.add(new EventInfo(m, module, m.getAnnotation(EventListener.class).priority()));
				listeners.put(m.getParameterTypes()[0], methods);
				continue;
			}

			infos.add(new EventInfo(m, module, m.getAnnotation(EventListener.class).priority()));

			sort(infos);

		}
	}
	public void unregister(Object module) {
		for(List<EventInfo> entry : listeners.values()) {
			entry.removeIf(eventInfo -> eventInfo.module == module);
		}
	}
	
	public void call(Event event) {
		List<EventInfo> info = listeners.get(event.getClass());
		
		
		if(info == null || info.isEmpty()) return;
		
		for(EventInfo i : info) {
			try {
				i.method.invoke(i.module, event);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		scriptListener(event);
	}
	
	private void scriptListener(Event e) {
	}
	private void sort(List<EventInfo> list) {
        list.sort((o1, o2) -> {
            return o1.priority.getValue() < o2.priority.getValue() ? -1 : 1;
        });
        Collections.reverse(list);
    }
	
	private class EventInfo {
		
		public Method method;
		
		public Object module;
		
		public EventPriority priority;

		public EventInfo(Method method, Object module2, EventPriority priority) {
			this.method = method;
			this.module = module2;
			this.priority = priority;
		}

	}
}
