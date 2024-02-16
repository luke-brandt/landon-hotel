package com.linkedin.learning.linkedinlearningfullstackappangularspringboot.config;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter.ReservationEntityToReservationResponseConverter;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter.ReservationRequestToReservationEntityConverter;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter.RoomEntityToReservableRoomResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

    private Set<Converter> getConverters(){
        Set<Converter> converters = new HashSet<Converter>();
        converters.add(new RoomEntityToReservableRoomResponseConverter());
        converters.add(new ReservationEntityToReservationResponseConverter());
        converters.add(new ReservationRequestToReservationEntityConverter());


        return converters;
    }

    @Bean
    public ConversionService conversionService(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }
}
