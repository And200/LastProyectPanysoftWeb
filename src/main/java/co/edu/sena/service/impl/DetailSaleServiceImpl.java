package co.edu.sena.service.impl;

import co.edu.sena.domain.DetailSale;
import co.edu.sena.repository.DetailSaleRepository;
import co.edu.sena.service.DetailSaleService;
import co.edu.sena.service.dto.DetailSaleDTO;
import co.edu.sena.service.mapper.DetailSaleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetailSale}.
 */
@Service
@Transactional
public class DetailSaleServiceImpl implements DetailSaleService {

    private final Logger log = LoggerFactory.getLogger(DetailSaleServiceImpl.class);

    private final DetailSaleRepository detailSaleRepository;

    private final DetailSaleMapper detailSaleMapper;

    public DetailSaleServiceImpl(DetailSaleRepository detailSaleRepository, DetailSaleMapper detailSaleMapper) {
        this.detailSaleRepository = detailSaleRepository;
        this.detailSaleMapper = detailSaleMapper;
    }

    @Override
    public DetailSaleDTO save(DetailSaleDTO detailSaleDTO) {
        log.debug("Request to save DetailSale : {}", detailSaleDTO);
        DetailSale detailSale = detailSaleMapper.toEntity(detailSaleDTO);
        detailSale = detailSaleRepository.save(detailSale);
        return detailSaleMapper.toDto(detailSale);
    }

    @Override
    public DetailSaleDTO update(DetailSaleDTO detailSaleDTO) {
        log.debug("Request to save DetailSale : {}", detailSaleDTO);
        DetailSale detailSale = detailSaleMapper.toEntity(detailSaleDTO);
        detailSale = detailSaleRepository.save(detailSale);
        return detailSaleMapper.toDto(detailSale);
    }

    @Override
    public Optional<DetailSaleDTO> partialUpdate(DetailSaleDTO detailSaleDTO) {
        log.debug("Request to partially update DetailSale : {}", detailSaleDTO);

        return detailSaleRepository
            .findById(detailSaleDTO.getId())
            .map(existingDetailSale -> {
                detailSaleMapper.partialUpdate(existingDetailSale, detailSaleDTO);

                return existingDetailSale;
            })
            .map(detailSaleRepository::save)
            .map(detailSaleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetailSaleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailSales");
        return detailSaleRepository.findAll(pageable).map(detailSaleMapper::toDto);
    }

    public Page<DetailSaleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return detailSaleRepository.findAllWithEagerRelationships(pageable).map(detailSaleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetailSaleDTO> findOne(Long id) {
        log.debug("Request to get DetailSale : {}", id);
        return detailSaleRepository.findOneWithEagerRelationships(id).map(detailSaleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailSale : {}", id);
        detailSaleRepository.deleteById(id);
    }
}
