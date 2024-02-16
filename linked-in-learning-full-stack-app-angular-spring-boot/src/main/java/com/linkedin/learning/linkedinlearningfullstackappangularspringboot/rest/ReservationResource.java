package com.linkedin.learning.linkedinlearningfullstackappangularspringboot.rest;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.converter.RoomEntityToReservableRoomResponseConverter;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.ReservationEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.RoomEntity;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.request.ReservationRequest;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.model.response.ReservableRoomResponse;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.PageableRoomRepository;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.ReservationRepository;
import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
public class ReservationResource {

    @Autowired
    PageableRoomRepository pageableRoomRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ConversionService conversionService;

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkin,
            @RequestParam(value = "checkout")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkout, Pageable pageable){

        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);

        return roomEntityList.map((source) -> {
            RoomEntityToReservableRoomResponseConverter roomEntityToReservableRoomResponseConverter = new RoomEntityToReservableRoomResponseConverter();
            return roomEntityToReservableRoomResponseConverter.convert(source);
        });
    }



//    source -> {
//        ReservationResponse reservationResponse = new ReservationResponse();
//        reservationResponse.setRoomNumber(source.getRoomNumber());
//        reservationResponse.setPrice(Integer.valueOf(source.getPrice()));
//
//        Links links = new Links();
//        Self self = new Self();
//        self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
//        links.setSelf(self);
//
//        reservationResponse.setLinks(links);
//
//        return reservationResponse;
//    }

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(
            @PathVariable
            Long roomId){


        RoomEntity roomEntity = roomRepository.findALLById(roomId);
        return new ResponseEntity<>(roomEntity, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservableRoomResponse> createReservation(
            @RequestBody
            ReservationRequest reservationRequest
    ){
        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
        reservationRepository.save(reservationEntity);

        RoomEntity roomEntity =  roomRepository.findALLById(reservationRequest.getRoomId());
        roomEntity.addReservationEntity(reservationEntity);
        roomRepository.save(roomEntity);

        return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservableRoomResponse> updateReservation(
            @RequestBody
            ReservationRequest reservationRequest
    ){
        return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(
            @PathVariable
            long reservationId){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
