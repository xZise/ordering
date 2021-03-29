package de.xzise.ordering;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
	    runOrdering(new OnlyValidOrdering(5));
		//runOrdering(new LengthOrdering(5));
    }

    private static void runOrdering(AbstractOrdering ordering) {
		Instant start = Instant.now();
		ordering.run();
		Instant end = Instant.now();
		ordering.printResults();
		System.out.println(String.format("Time elapsed: %s ms", Duration.between(start, end).toMillis()));
	}


}
