package com.bhupi.spring6restmvc.mappers;

import com.bhupi.spring6restmvc.entities.Beer;
import com.bhupi.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
