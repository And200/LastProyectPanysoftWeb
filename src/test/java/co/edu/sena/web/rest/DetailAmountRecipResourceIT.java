package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.DetailAmountRecip;
import co.edu.sena.domain.Product;
import co.edu.sena.domain.Recip;
import co.edu.sena.repository.DetailAmountRecipRepository;
import co.edu.sena.service.DetailAmountRecipService;
import co.edu.sena.service.dto.DetailAmountRecipDTO;
import co.edu.sena.service.mapper.DetailAmountRecipMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DetailAmountRecipResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DetailAmountRecipResourceIT {

    private static final Double DEFAULT_AMOUNT_PRODUCT = 1D;
    private static final Double UPDATED_AMOUNT_PRODUCT = 2D;

    private static final String ENTITY_API_URL = "/api/detail-amount-recips";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailAmountRecipRepository detailAmountRecipRepository;

    @Mock
    private DetailAmountRecipRepository detailAmountRecipRepositoryMock;

    @Autowired
    private DetailAmountRecipMapper detailAmountRecipMapper;

    @Mock
    private DetailAmountRecipService detailAmountRecipServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailAmountRecipMockMvc;

    private DetailAmountRecip detailAmountRecip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailAmountRecip createEntity(EntityManager em) {
        DetailAmountRecip detailAmountRecip = new DetailAmountRecip().amountProduct(DEFAULT_AMOUNT_PRODUCT);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        detailAmountRecip.setProduct(product);
        // Add required entity
        Recip recip;
        if (TestUtil.findAll(em, Recip.class).isEmpty()) {
            recip = RecipResourceIT.createEntity(em);
            em.persist(recip);
            em.flush();
        } else {
            recip = TestUtil.findAll(em, Recip.class).get(0);
        }
        detailAmountRecip.setRecip(recip);
        return detailAmountRecip;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailAmountRecip createUpdatedEntity(EntityManager em) {
        DetailAmountRecip detailAmountRecip = new DetailAmountRecip().amountProduct(UPDATED_AMOUNT_PRODUCT);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        detailAmountRecip.setProduct(product);
        // Add required entity
        Recip recip;
        if (TestUtil.findAll(em, Recip.class).isEmpty()) {
            recip = RecipResourceIT.createUpdatedEntity(em);
            em.persist(recip);
            em.flush();
        } else {
            recip = TestUtil.findAll(em, Recip.class).get(0);
        }
        detailAmountRecip.setRecip(recip);
        return detailAmountRecip;
    }

    @BeforeEach
    public void initTest() {
        detailAmountRecip = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailAmountRecip() throws Exception {
        int databaseSizeBeforeCreate = detailAmountRecipRepository.findAll().size();
        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);
        restDetailAmountRecipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeCreate + 1);
        DetailAmountRecip testDetailAmountRecip = detailAmountRecipList.get(detailAmountRecipList.size() - 1);
        assertThat(testDetailAmountRecip.getAmountProduct()).isEqualTo(DEFAULT_AMOUNT_PRODUCT);
    }

    @Test
    @Transactional
    void createDetailAmountRecipWithExistingId() throws Exception {
        // Create the DetailAmountRecip with an existing ID
        detailAmountRecip.setId(1L);
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        int databaseSizeBeforeCreate = detailAmountRecipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailAmountRecipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountProductIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailAmountRecipRepository.findAll().size();
        // set the field null
        detailAmountRecip.setAmountProduct(null);

        // Create the DetailAmountRecip, which fails.
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        restDetailAmountRecipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isBadRequest());

        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailAmountRecips() throws Exception {
        // Initialize the database
        detailAmountRecipRepository.saveAndFlush(detailAmountRecip);

        // Get all the detailAmountRecipList
        restDetailAmountRecipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailAmountRecip.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountProduct").value(hasItem(DEFAULT_AMOUNT_PRODUCT.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetailAmountRecipsWithEagerRelationshipsIsEnabled() throws Exception {
        when(detailAmountRecipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetailAmountRecipMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(detailAmountRecipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetailAmountRecipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(detailAmountRecipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetailAmountRecipMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(detailAmountRecipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDetailAmountRecip() throws Exception {
        // Initialize the database
        detailAmountRecipRepository.saveAndFlush(detailAmountRecip);

        // Get the detailAmountRecip
        restDetailAmountRecipMockMvc
            .perform(get(ENTITY_API_URL_ID, detailAmountRecip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailAmountRecip.getId().intValue()))
            .andExpect(jsonPath("$.amountProduct").value(DEFAULT_AMOUNT_PRODUCT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDetailAmountRecip() throws Exception {
        // Get the detailAmountRecip
        restDetailAmountRecipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetailAmountRecip() throws Exception {
        // Initialize the database
        detailAmountRecipRepository.saveAndFlush(detailAmountRecip);

        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();

        // Update the detailAmountRecip
        DetailAmountRecip updatedDetailAmountRecip = detailAmountRecipRepository.findById(detailAmountRecip.getId()).get();
        // Disconnect from session so that the updates on updatedDetailAmountRecip are not directly saved in db
        em.detach(updatedDetailAmountRecip);
        updatedDetailAmountRecip.amountProduct(UPDATED_AMOUNT_PRODUCT);
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(updatedDetailAmountRecip);

        restDetailAmountRecipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailAmountRecipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
        DetailAmountRecip testDetailAmountRecip = detailAmountRecipList.get(detailAmountRecipList.size() - 1);
        assertThat(testDetailAmountRecip.getAmountProduct()).isEqualTo(UPDATED_AMOUNT_PRODUCT);
    }

    @Test
    @Transactional
    void putNonExistingDetailAmountRecip() throws Exception {
        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();
        detailAmountRecip.setId(count.incrementAndGet());

        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailAmountRecipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailAmountRecipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailAmountRecip() throws Exception {
        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();
        detailAmountRecip.setId(count.incrementAndGet());

        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailAmountRecipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailAmountRecip() throws Exception {
        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();
        detailAmountRecip.setId(count.incrementAndGet());

        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailAmountRecipMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailAmountRecipWithPatch() throws Exception {
        // Initialize the database
        detailAmountRecipRepository.saveAndFlush(detailAmountRecip);

        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();

        // Update the detailAmountRecip using partial update
        DetailAmountRecip partialUpdatedDetailAmountRecip = new DetailAmountRecip();
        partialUpdatedDetailAmountRecip.setId(detailAmountRecip.getId());

        restDetailAmountRecipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailAmountRecip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailAmountRecip))
            )
            .andExpect(status().isOk());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
        DetailAmountRecip testDetailAmountRecip = detailAmountRecipList.get(detailAmountRecipList.size() - 1);
        assertThat(testDetailAmountRecip.getAmountProduct()).isEqualTo(DEFAULT_AMOUNT_PRODUCT);
    }

    @Test
    @Transactional
    void fullUpdateDetailAmountRecipWithPatch() throws Exception {
        // Initialize the database
        detailAmountRecipRepository.saveAndFlush(detailAmountRecip);

        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();

        // Update the detailAmountRecip using partial update
        DetailAmountRecip partialUpdatedDetailAmountRecip = new DetailAmountRecip();
        partialUpdatedDetailAmountRecip.setId(detailAmountRecip.getId());

        partialUpdatedDetailAmountRecip.amountProduct(UPDATED_AMOUNT_PRODUCT);

        restDetailAmountRecipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailAmountRecip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailAmountRecip))
            )
            .andExpect(status().isOk());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
        DetailAmountRecip testDetailAmountRecip = detailAmountRecipList.get(detailAmountRecipList.size() - 1);
        assertThat(testDetailAmountRecip.getAmountProduct()).isEqualTo(UPDATED_AMOUNT_PRODUCT);
    }

    @Test
    @Transactional
    void patchNonExistingDetailAmountRecip() throws Exception {
        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();
        detailAmountRecip.setId(count.incrementAndGet());

        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailAmountRecipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailAmountRecipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailAmountRecip() throws Exception {
        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();
        detailAmountRecip.setId(count.incrementAndGet());

        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailAmountRecipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailAmountRecip() throws Exception {
        int databaseSizeBeforeUpdate = detailAmountRecipRepository.findAll().size();
        detailAmountRecip.setId(count.incrementAndGet());

        // Create the DetailAmountRecip
        DetailAmountRecipDTO detailAmountRecipDTO = detailAmountRecipMapper.toDto(detailAmountRecip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailAmountRecipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailAmountRecipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailAmountRecip in the database
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailAmountRecip() throws Exception {
        // Initialize the database
        detailAmountRecipRepository.saveAndFlush(detailAmountRecip);

        int databaseSizeBeforeDelete = detailAmountRecipRepository.findAll().size();

        // Delete the detailAmountRecip
        restDetailAmountRecipMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailAmountRecip.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailAmountRecip> detailAmountRecipList = detailAmountRecipRepository.findAll();
        assertThat(detailAmountRecipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
