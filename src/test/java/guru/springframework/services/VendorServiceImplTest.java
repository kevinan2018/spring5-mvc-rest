package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.then;

public class VendorServiceImplTest {

    private static final String VEND_URL = VendorController.BASE_URL + "/";
    private static final String NAME_1 = "Vendor 1";
    private static final long ID_1 = 1L;
    private static final String NAME_2 = "Vendor 2";
    private static final long ID_2 = 2L;

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(getVendor1st(), getVendor2nd());
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
       VendorListDTO vendorsDTOS = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorsDTOS.getVendors().size(), is(equalTo(2)));

    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = getVendor1st();

        //mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

        //JUnit assert that with matcher
        assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {
        //given
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor savedVendor = getVendor1st();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        //should() defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
        assertEquals(savedVendorDTO.getName(), savedVendor.getName());
        assertEquals(savedVendorDTO.getVendorUrl(), VEND_URL + "1");
    }

    @Test
    public void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor savedVendor = getVendor1st();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO(ID_1, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedDto.getVendorUrl(), containsString("1"));
        assertEquals(savedDto.getName(), NAME_1);
        assertEquals(savedDto.getVendorUrl(), VEND_URL + "1");

    }

    @Test
    public void patchVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor patchedVendor = getVendor1st();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(patchedVendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(patchedVendor);

        //when
        VendorDTO patchedDto = vendorService.patchVendor(ID_1, vendorDTO);

        //then
        then(vendorRepository).should().findById(anyLong());
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(patchedDto.getVendorUrl(), containsString("1"));
        assertEquals(patchedDto.getName(), vendorDTO.getName());
        assertEquals(patchedDto.getVendorUrl(), VEND_URL + "1");

    }

    @Test
    public void deleteVendorById() {
        Long id = 1L;

        vendorService.deleteVendorById(id);

        then(vendorRepository).should().deleteById(anyLong());
    }

    private Vendor getVendor1st() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return  vendor;
    }

    private Vendor getVendor2nd() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return  vendor;
    }
}