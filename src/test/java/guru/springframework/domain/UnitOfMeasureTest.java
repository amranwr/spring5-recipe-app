package guru.springframework.domain;

import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureTest {
    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findById(1L);
    }

    @Test
    void getUom() {
        //Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByUom("Teaspoon");
        //assertEquals("Teaspoon",optional.get().getUom());
    }
}