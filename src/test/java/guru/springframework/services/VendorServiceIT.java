package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorMapper vendorMapper;
    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading vendor data:");
        System.out.println(vendorRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper.INSTANCE);
    }

    @Test
    public void patchVendorUpdateName() throws Exception {
        String updatedName = "Vendor patched";
        Long id = getVendorIdValue();

        Vendor origVendor = vendorRepository.getOne(id);
        assertNotNull(origVendor);
        String origName = origVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        //when
        VendorDTO patchedVendorDto = vendorService.patchVendor(id, vendorDTO);

        //then
        Vendor savedVendor = vendorRepository.findById(id).get();
        assertNotNull(savedVendor);

        assertThat(patchedVendorDto.getName(), not(equalTo(origName)));
        assertThat(patchedVendorDto.getName(), equalTo(updatedName));
        assertThat(savedVendor.getName(), equalTo(updatedName));

    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();

        System.out.println("Vendors Found: " + vendors.size());

        return vendors.get(0).getId();
    }

}
