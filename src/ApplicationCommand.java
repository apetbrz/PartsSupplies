public enum ApplicationCommand {
    
    EXIT_APPLICATION(null),
    HELP_MESSAGE(null),
    LIST_PART(ApplicationFunctions.listPart),
    LIST_SUPPLIER(ApplicationFunctions.listSuppliers),
    LIST_CATALOG_ENTRY(ApplicationFunctions.listCatalogEntries),
    ADD_PART(ApplicationFunctions.addPart),
    ADD_SUPPLIER(ApplicationFunctions.addSupplier),
    ADD_CATALOG_ENTRY(ApplicationFunctions.addCatalogEntry),
    DEL_PART(ApplicationFunctions.deletePart),
    DEL_SUPPLIER(ApplicationFunctions.deleteSupplier),
    DEL_CATALOG_ENTRY(ApplicationFunctions.deleteCatalogEntry),
    UPD_CATALOG_ENTRY(ApplicationFunctions.updateCatalogEntry),
    QUERY_PART(ApplicationFunctions.queryPartSuppliers),
    QUERY_CHEAPEST(ApplicationFunctions.queryCheapestPartSupplier);

    public final SQLFunction func;

    private ApplicationCommand(SQLFunction func){
        this.func = func;
    }
}