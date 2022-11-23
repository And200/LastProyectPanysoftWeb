package co.edu.sena.service.impl;

import co.edu.sena.domain.PurchaseReceipt;
import co.edu.sena.repository.PurchaseReceiptRepository;
import co.edu.sena.service.PurchaseReceiptService;
import co.edu.sena.service.dto.PurchaseReceiptDTO;
import co.edu.sena.service.mapper.PurchaseReceiptMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PurchaseReceipt}.
 */
@Service
@Transactional
public class PurchaseReceiptServiceImpl implements PurchaseReceiptService {

    private final Logger log = LoggerFactory.getLogger(PurchaseReceiptServiceImpl.class);

    private final PurchaseReceiptRepository purchaseReceiptRepository;

    private final PurchaseReceiptMapper purchaseReceiptMapper;

    public PurchaseReceiptServiceImpl(PurchaseReceiptRepository purchaseReceiptRepository, PurchaseReceiptMapper purchaseReceiptMapper) {
        this.purchaseReceiptRepository = purchaseReceiptRepository;
        this.purchaseReceiptMapper = purchaseReceiptMapper;
    }

    @Override
    public PurchaseReceiptDTO save(PurchaseReceiptDTO purchaseReceiptDTO) {
        log.debug("Request to save PurchaseReceipt : {}", purchaseReceiptDTO);
        PurchaseReceipt purchaseReceipt = purchaseReceiptMapper.toEntity(purchaseReceiptDTO);
        purchaseReceipt = purchaseReceiptRepository.save(purchaseReceipt);
        return purchaseReceiptMapper.toDto(purchaseReceipt);
    }

    @Override
    public PurchaseReceiptDTO update(PurchaseReceiptDTO purchaseReceiptDTO) {
        log.debug("Request to save PurchaseReceipt : {}", purchaseReceiptDTO);
        PurchaseReceipt purchaseReceipt = purchaseReceiptMapper.toEntity(purchaseReceiptDTO);
        purchaseReceipt = purchaseReceiptRepository.save(purchaseReceipt);
        return purchaseReceiptMapper.toDto(purchaseReceipt);
    }

    @Override
    public Optional<PurchaseReceiptDTO> partialUpdate(PurchaseReceiptDTO purchaseReceiptDTO) {
        log.debug("Request to partially update PurchaseReceipt : {}", purchaseReceiptDTO);

        return purchaseReceiptRepository
            .findById(purchaseReceiptDTO.getId())
            .map(existingPurchaseReceipt -> {
                purchaseReceiptMapper.partialUpdate(existingPurchaseReceipt, purchaseReceiptDTO);

                return existingPurchaseReceipt;
            })
            .map(purchaseReceiptRepository::save)
            .map(purchaseReceiptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseReceiptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseReceipts");
        return purchaseReceiptRepository.findAll(pageable).map(purchaseReceiptMapper::toDto);
    }

    public Page<PurchaseReceiptDTO> findAllWithEagerRelationships(Pageable pageable) {
        return purchaseReceiptRepository.findAllWithEagerRelationships(pageable).map(purchaseReceiptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseReceiptDTO> findOne(Long id) {
        log.debug("Request to get PurchaseReceipt : {}", id);
        return purchaseReceiptRepository.findOneWithEagerRelationships(id).map(purchaseReceiptMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseReceipt : {}", id);
        purchaseReceiptRepository.deleteById(id);
    }
}
