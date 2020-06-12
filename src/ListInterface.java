public interface ListInterface<T>
	{
		int size();
		
		void add(T element);

		void find(T target);

		boolean remove(T element);
		
		T get(T element);
		
		String toString();
		
		void reset();
		
		T getNext();
		
	}
