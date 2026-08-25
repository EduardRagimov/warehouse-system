package com.warehouse.auth_service.entity;

public enum Role {
    /**
     * All privileges
     */
    ADMIN,

    /**
     * Inventory service user,
     * Can add/remove inventory entries
     */
    USER,

    /**
     * Order service user,
     * Can allocate/deallocate orders
     */
    CUSTOMER,
}
