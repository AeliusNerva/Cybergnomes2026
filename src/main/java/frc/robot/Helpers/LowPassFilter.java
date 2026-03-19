package frc.robot.helpers;

import frc.robot.Constants;

public class LowPassFilter {
	private static final int low_pass_max_samples = Constants.Filters.LOW_PASS_MAX_SAMPLES;

	public void push_back(double[] array, double item) {
		for (int i = 0; i < array.length - 1; i++) {
			array[i] = array[i + 1];
		}

		if (array.length > 0) {
			array[array.length - 1] = item;
		}
	}

	private double[] low_pass_array = new double[low_pass_max_samples];
	private int low_pass_samples = 0;

	public double low_pass(double new_input) {
		if (low_pass_samples < low_pass_array.length) {
			low_pass_array[low_pass_samples] = new_input;
			low_pass_samples++;
		} else {
			push_back(low_pass_array, new_input);
		}
		double sum = 0.0;
		for (int i = 0; i < low_pass_array.length; i++) {
			sum += low_pass_array[i];
		}

		return sum / low_pass_samples;
	}
}