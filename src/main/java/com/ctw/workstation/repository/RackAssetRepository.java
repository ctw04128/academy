package com.ctw.workstation.repository;

import com.ctw.workstation.item.RackAsset;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RackAssetRepository implements PanacheRepository<RackAsset> {

    private static final String BY_ASSET_TAG = "assetTag";
    public RackAsset findByAssetTag(String assetTag) {
        return find(BY_ASSET_TAG, assetTag).firstResult();
    }
}
