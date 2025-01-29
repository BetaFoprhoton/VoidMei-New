package org.matrixsukhoi.voidmei.prog;

public class CalcHelper {
	public static class SimpleMovingAverage {
		double[] data;
		int cnt;
		int n;
		int ready;
		double avg;
		public SimpleMovingAverage(int num) {
			data = new double[num];
			n = num;
			cnt = 0;
			ready = 0;
			avg = 0;
		}
		public double addNewData(double newData){
			if (ready == 0 && cnt < n ) {
				// 添加数据
				data[cnt++] = newData;
				double tavg = 0;
				for (int i = 0; i < cnt; i++){
					tavg += data[i];
				}
				avg = tavg/cnt;
//				return 0;
			} else {

				ready = 1;
				int ridx = cnt++ % n; 
				avg = avg + (newData - data[ridx])/n;
				data[ridx] = newData;
			}
			return avg;
		}
	}
}
