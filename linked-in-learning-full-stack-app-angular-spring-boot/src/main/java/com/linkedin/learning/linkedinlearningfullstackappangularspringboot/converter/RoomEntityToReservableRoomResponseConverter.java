package com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.RoomEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.Links;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.Self;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.response.ReservableRoomResponse;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

    @Override
    public ReservableRoomResponse convert(RoomEntity source) {

        ReservableRoomResponse reservableRoomResponse = new ReservableRoomResponse();
        reservableRoomResponse.setRoomNumber(source.getRoomNumber());
        reservableRoomResponse.setPrice(Integer.valueOf(source.getPrice()));

        Links links = new Links();
        Self self = new Self();
        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
        links.setSelf(self);

        reservableRoomResponse.setLinks(links);
        return reservableRoomResponse;
    }
}
