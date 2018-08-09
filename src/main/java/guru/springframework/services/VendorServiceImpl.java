package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;

import java.util.List;

public class VendorServiceImpl implements VendorService {
    @Override
    public List<VendorDTO> getAllCustomers() {
        return null;
    }

    @Override
    public VendorDTO getCustomerById(Long id) {
        return null;
    }

    @Override
    public VendorDTO createNewCustomer(VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO saveCustomerByDTO(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO patchCustomer(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public void deleteVendorById(Long id) {

    }
}
