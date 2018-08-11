package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {
    private final String NAME = "fresh";
    private final VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(NAME);

        VendorDTO vendorDTO = mapper.VendorToVendorDTO(vendor);

        assertEquals(vendorDTO.getName(), NAME);
    }

    @Test
    public void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = mapper.VendorDTOToVendor(vendorDTO);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }
}