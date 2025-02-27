package com.ctw.workstation.item;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "T_RACK_ASSET")
public class RackAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackAssetIdGenerator")
    @SequenceGenerator(name = "rackAssetIdGenerator", sequenceName = "SEQ_RACK_ASSET_ID")
    private Long id;
    @Column(name = "ASSET_TAG", length = 10, nullable = false)
    private String assetTag;
    @Column(name = "RACK_ID")
    private Long rackId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RACK_ID", updatable = false, insertable = false, nullable = false)
    private Rack rack;

    protected RackAsset() {}

    private RackAsset(String assetTag, Long rackId) {
        this.assetTag = assetTag;
        this.rackId = rackId;
    }

    public long getId() {
        return id;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public static class Builder {
        private String assetTag;
        private Long rackId;
        public Builder setAssetTag(String assetTag) {
            this.assetTag = assetTag;
            return this;
        }
        public Builder setRackId(Long rackId) {
            this.rackId = rackId;
            return this;
        }
        public RackAsset build() {
            return new RackAsset(assetTag, rackId);
        }
    }
}
