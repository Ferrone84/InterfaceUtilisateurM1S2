package viewer.utilities;

import javafx.event.Event;
import javafx.event.EventType;

public class RichEvent<T> extends Event {
	private T data;
	public RichEvent(T data) {
		super(null, null, EventType.ROOT);
		this.data = data;
	}

	public T GetData() {
		return this.data;
	}
}
