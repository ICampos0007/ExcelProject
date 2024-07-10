package org.IrvinCampos;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;

public class testSample {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        DataDriven dataDriven = new DataDriven();
        ArrayList<String> data = dataDriven.getData("Add Profile");

        for (int i=0; i< data.size(); i++) {
            System.out.println(data.get(i));
        }
//        System.out.println(data.get(0));
//        System.out.println(data.get(1));
//        System.out.println(data.get(2));
//        System.out.println(data.get(3));
    }
}
