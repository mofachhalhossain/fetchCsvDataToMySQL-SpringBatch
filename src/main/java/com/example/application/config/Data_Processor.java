package com.example.application.config;
import com.example.application.model.Data_Init;
import org.springframework.batch.item.ItemProcessor;


public class Data_Processor implements ItemProcessor<Data_Init, Data_Init>{
    @Override
    public Data_Init process(Data_Init data_init) throws Exception {
        return data_init;
    }
}
