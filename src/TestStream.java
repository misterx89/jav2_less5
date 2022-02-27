package less5;

import java.util.Arrays;

public class TestStream {
    static final int ARR_SIZE = 10000000;
    static final int ARR_HALF = ARR_SIZE / 2;

    public static void main(String[] args) {

        float[] arr = new float[ARR_SIZE];
        fArr(arr);
        Metod1(arr);

        fArr(arr);
        Metod2(arr);

    }

    public static void fArr(float[] arr) {
        Arrays.fill(arr, 1);
    }

    public static void Metod1(float[] arr) {
        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("??????? 1 ?????????? ?? " + (timeStart - timeFinish) + "??");
        System.out.println(arr[ARR_HALF]);
    }

    public static void Metod2(float[] arr) {

        long timeStart = System.currentTimeMillis();

        float[] arr1 = new float[ARR_HALF];
        float[] arr2 = new float[ARR_HALF];

        System.arraycopy(arr,0, arr1, 0, ARR_HALF);
        System.arraycopy(arr, ARR_HALF, arr2, 0, ARR_HALF);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = (float) (arr[i] * Math.sin(0.2f + (i + ARR_HALF) / 5) * Math.cos(0.4f + (i + ARR_HALF) / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1,0, arr, 0, ARR_HALF);
        System.arraycopy(arr2, 0, arr, ARR_HALF, ARR_HALF);

        long timeFinish = System.currentTimeMillis();
        System.out.println("??????? 2 ?????????? ?? " + (timeStart - timeFinish) + "??");
        System.out.println(arr[ARR_HALF]);
    }

}
