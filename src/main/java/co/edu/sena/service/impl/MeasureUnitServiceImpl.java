package co.edu.sena.service.impl;

import co.edu.sena.domain.MeasureUnit;
import co.edu.sena.repository.MeasureUnitRepository;
import co.edu.sena.service.MeasureUnitService;
import co.edu.sena.service.dto.MeasureUnitDTO;
import co.edu.sena.service.mapper.MeasureUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MeasureUnit}.
 */
@Service
@Transactional
public class MeasureUnitServiceImpl implements MeasureUnitService {

    private final Logger log = LoggerFactory.getLogger(MeasureUnitServiceImpl.class);

    private final MeasureUnitRepository measureUnitRepository;

    private final MeasureUnitMapper measureUnitMapper;

    public MeasureUnitServiceImpl(MeasureUnitRepository measureUnitRepository, MeasureUnitMapper measureUnitMapper) {
        this.measureUnitRepository = measureUnitRepository;
        this.measureUnitMapper = measureUnitMapper;
    }

    @Override
    public MeasureUnitDTO save(MeasureUnitDTO measureUnitDTO) {
        log.debug("Request to save MeasureUnit : {}", measureUnitDTO);
        MeasureUnit measureUnit = measureUnitMapper.toEntity(measureUnitDTO);
        measureUnit = measureUnitRepository.save(measureUnit);
        return measureUnitMapper.toDto(measureUnit);
    }

    @Override
    public MeasureUnitDTO update(MeasureUnitDTO measureUnitDTO) {
        log.debug("Request to save MeasureUnit : {}", measureUnitDTO);
        MeasureUnit measureUnit = measureUnitMapper.toEntity(measureUnitDTO);
        measureUnit = measureUnitRepository.save(measureUnit);
        return measureUnitMapper.toDto(measureUnit);
    }

    @Override
    public Optional<MeasureUnitDTO> partialUpdate(MeasureUnitDTO measureUnitDTO) {
        log.debug("Request to partially update MeasureUnit : {}", measureUnitDTO);

        return measureUnitRepository
            .findById(measureUnitDTO.getId())
            .map(existingMeasureUnit -> {
                measureUnitMapper.partialUpdate(existingMeasureUnit, measureUnitDTO);

                return existingMeasureUnit;
            })
            .map(measureUnitRepository::save)
            .map(measureUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MeasureUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeasureUnits");
        return measureUnitRepository.findAll(pageable).map(measureUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MeasureUnitDTO> findOne(Long id) {
        log.debug("Request to get MeasureUnit : {}", id);
        return measureUnitRepository.findById(id).map(measureUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeasureUnit : {}", id);
        measureUnitRepository.deleteById(id);
    }
}
