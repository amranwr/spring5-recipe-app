package guru.springframework.services.springDataJPA;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasureCommand> getUomList() {
        Set<UnitOfMeasureCommand> unitOfMeasures = new HashSet<>();
        ArrayList<UnitOfMeasure> unitOfMeasureSet= (ArrayList<UnitOfMeasure>) unitOfMeasureRepository.findAll();
        UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();
        for(UnitOfMeasure unitOfMeasure:unitOfMeasureSet){
            unitOfMeasures.add(converter.convert(unitOfMeasure));
        }
        return unitOfMeasures;
    }
}
