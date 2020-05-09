package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Brand;
import co.lq.modules.shop.domain.StoreCategory;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.modules.shop.service.param.MemberPriceParam;
import co.lq.modules.shop.service.param.PrefrenceAreaProductRelationParam;
import co.lq.modules.shop.service.param.ProductAttributeValueParam;
import co.lq.modules.shop.service.param.ProductFullReductionParam;
import co.lq.modules.shop.service.param.ProductLadderParam;
import co.lq.modules.shop.service.param.SkuStockParam;
import co.lq.modules.shop.service.param.SubjectProductRelationParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-09T22:21:13+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_40 (Oracle Corporation)"
)
@Component
public class StoreProductMapperImpl implements StoreProductMapper {

    @Override
    public StoreProduct toEntity(StoreProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreProduct storeProduct = new StoreProduct();

        storeProduct.setId( dto.getId() );
        storeProduct.setImage( dto.getImage() );
        storeProduct.setSliderImage( dto.getSliderImage() );
        storeProduct.setProductName( dto.getProductName() );
        storeProduct.setStoreInfo( dto.getStoreInfo() );
        storeProduct.setKeyword( dto.getKeyword() );
        storeProduct.setBarCode( dto.getBarCode() );
        storeProduct.setStoreCateId( dto.getStoreCateId() );
        storeProduct.setBrandId( dto.getBrandId() );
        storeProduct.setPrice( dto.getPrice() );
        storeProduct.setVipPrice( dto.getVipPrice() );
        storeProduct.setOtPrice( dto.getOtPrice() );
        storeProduct.setPostage( dto.getPostage() );
        storeProduct.setUnitName( dto.getUnitName() );
        storeProduct.setSort( dto.getSort() );
        storeProduct.setSales( dto.getSales() );
        storeProduct.setStock( dto.getStock() );
        storeProduct.setIsShow( dto.getIsShow() );
        storeProduct.setIsHot( dto.getIsHot() );
        storeProduct.setIsBenefit( dto.getIsBenefit() );
        storeProduct.setIsBest( dto.getIsBest() );
        storeProduct.setIsNew( dto.getIsNew() );
        storeProduct.setDescription( dto.getDescription() );
        storeProduct.setAddTime( dto.getAddTime() );
        storeProduct.setIsPostage( dto.getIsPostage() );
        storeProduct.setIsDel( dto.getIsDel() );
        storeProduct.setMerUse( dto.getMerUse() );
        storeProduct.setGiveIntegral( dto.getGiveIntegral() );
        storeProduct.setCost( dto.getCost() );
        storeProduct.setFeightTemplateId( dto.getFeightTemplateId() );
        storeProduct.setIsSeckill( dto.getIsSeckill() );
        storeProduct.setIsBargain( dto.getIsBargain() );
        storeProduct.setIsGood( dto.getIsGood() );
        storeProduct.setFicti( dto.getFicti() );
        storeProduct.setBrowse( dto.getBrowse() );
        storeProduct.setCodePath( dto.getCodePath() );
        storeProduct.setSoureLink( dto.getSoureLink() );
        storeProduct.setStoreId( dto.getStoreId() );
        storeProduct.setVerifyStatus( dto.getVerifyStatus() );
        storeProduct.setCatalogId( dto.getCatalogId() );
        storeProduct.setWeight( dto.getWeight() );
        List<MemberPriceParam> list = dto.getMemberPriceList();
        if ( list != null ) {
            storeProduct.setMemberPriceList( new ArrayList<MemberPriceParam>( list ) );
        }
        else {
            storeProduct.setMemberPriceList( null );
        }
        List<ProductFullReductionParam> list1 = dto.getProductFullReductionList();
        if ( list1 != null ) {
            storeProduct.setProductFullReductionList( new ArrayList<ProductFullReductionParam>( list1 ) );
        }
        else {
            storeProduct.setProductFullReductionList( null );
        }
        List<ProductLadderParam> list2 = dto.getProductLadderList();
        if ( list2 != null ) {
            storeProduct.setProductLadderList( new ArrayList<ProductLadderParam>( list2 ) );
        }
        else {
            storeProduct.setProductLadderList( null );
        }
        List<ProductAttributeValueParam> list3 = dto.getProductAttributeValueList();
        if ( list3 != null ) {
            storeProduct.setProductAttributeValueList( new ArrayList<ProductAttributeValueParam>( list3 ) );
        }
        else {
            storeProduct.setProductAttributeValueList( null );
        }
        List<SkuStockParam> list4 = dto.getSkuStockList();
        if ( list4 != null ) {
            storeProduct.setSkuStockList( new ArrayList<SkuStockParam>( list4 ) );
        }
        else {
            storeProduct.setSkuStockList( null );
        }
        List<SubjectProductRelationParam> list5 = dto.getSubjectProductRelationList();
        if ( list5 != null ) {
            storeProduct.setSubjectProductRelationList( new ArrayList<SubjectProductRelationParam>( list5 ) );
        }
        else {
            storeProduct.setSubjectProductRelationList( null );
        }
        List<PrefrenceAreaProductRelationParam> list6 = dto.getPrefrenceAreaProductRelationList();
        if ( list6 != null ) {
            storeProduct.setPrefrenceAreaProductRelationList( new ArrayList<PrefrenceAreaProductRelationParam>( list6 ) );
        }
        else {
            storeProduct.setPrefrenceAreaProductRelationList( null );
        }

        return storeProduct;
    }

    @Override
    public List<StoreProduct> toEntity(List<StoreProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreProduct> list = new ArrayList<StoreProduct>( dtoList.size() );
        for ( StoreProductDTO storeProductDTO : dtoList ) {
            list.add( toEntity( storeProductDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreProductDTO> toDto(List<StoreProduct> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreProductDTO> list = new ArrayList<StoreProductDTO>( entityList.size() );
        for ( StoreProduct storeProduct : entityList ) {
            list.add( toDto( storeProduct ) );
        }

        return list;
    }

    @Override
    public StoreProductDTO toDto(StoreProduct storeProduct) {
        if ( storeProduct == null ) {
            return null;
        }

        StoreProductDTO storeProductDTO = new StoreProductDTO();

        String cateName = storeProductStoreCategoryCateName( storeProduct );
        if ( cateName != null ) {
            storeProductDTO.setStoreCateName( cateName );
        }
        String name = storeProductBrandName( storeProduct );
        if ( name != null ) {
            storeProductDTO.setBrandName( name );
        }
        Long id = storeProductBrandId( storeProduct );
        if ( id != null ) {
            storeProductDTO.setBrandId( id );
        }
        Long id1 = storeProductStoreCategoryId( storeProduct );
        if ( id1 != null ) {
            storeProductDTO.setStoreCateId( id1 );
        }
        storeProductDTO.setId( storeProduct.getId() );
        storeProductDTO.setImage( storeProduct.getImage() );
        storeProductDTO.setSliderImage( storeProduct.getSliderImage() );
        storeProductDTO.setProductName( storeProduct.getProductName() );
        storeProductDTO.setStoreInfo( storeProduct.getStoreInfo() );
        storeProductDTO.setKeyword( storeProduct.getKeyword() );
        storeProductDTO.setBarCode( storeProduct.getBarCode() );
        storeProductDTO.setPrice( storeProduct.getPrice() );
        storeProductDTO.setVipPrice( storeProduct.getVipPrice() );
        storeProductDTO.setOtPrice( storeProduct.getOtPrice() );
        storeProductDTO.setPostage( storeProduct.getPostage() );
        storeProductDTO.setUnitName( storeProduct.getUnitName() );
        storeProductDTO.setSort( storeProduct.getSort() );
        storeProductDTO.setSales( storeProduct.getSales() );
        storeProductDTO.setStock( storeProduct.getStock() );
        storeProductDTO.setIsShow( storeProduct.getIsShow() );
        storeProductDTO.setIsHot( storeProduct.getIsHot() );
        storeProductDTO.setIsBenefit( storeProduct.getIsBenefit() );
        storeProductDTO.setIsBest( storeProduct.getIsBest() );
        storeProductDTO.setIsNew( storeProduct.getIsNew() );
        storeProductDTO.setDescription( storeProduct.getDescription() );
        storeProductDTO.setAddTime( storeProduct.getAddTime() );
        storeProductDTO.setIsPostage( storeProduct.getIsPostage() );
        storeProductDTO.setIsDel( storeProduct.getIsDel() );
        storeProductDTO.setMerUse( storeProduct.getMerUse() );
        storeProductDTO.setGiveIntegral( storeProduct.getGiveIntegral() );
        storeProductDTO.setCost( storeProduct.getCost() );
        storeProductDTO.setIsSeckill( storeProduct.getIsSeckill() );
        storeProductDTO.setIsBargain( storeProduct.getIsBargain() );
        storeProductDTO.setIsGood( storeProduct.getIsGood() );
        storeProductDTO.setFicti( storeProduct.getFicti() );
        storeProductDTO.setBrowse( storeProduct.getBrowse() );
        storeProductDTO.setCodePath( storeProduct.getCodePath() );
        storeProductDTO.setSoureLink( storeProduct.getSoureLink() );
        storeProductDTO.setStoreId( storeProduct.getStoreId() );
        storeProductDTO.setVerifyStatus( storeProduct.getVerifyStatus() );
        storeProductDTO.setCatalogId( storeProduct.getCatalogId() );
        storeProductDTO.setWeight( storeProduct.getWeight() );
        storeProductDTO.setFeightTemplateId( storeProduct.getFeightTemplateId() );
        List<MemberPriceParam> list = storeProduct.getMemberPriceList();
        if ( list != null ) {
            storeProductDTO.setMemberPriceList( new ArrayList<MemberPriceParam>( list ) );
        }
        else {
            storeProductDTO.setMemberPriceList( null );
        }
        List<ProductFullReductionParam> list1 = storeProduct.getProductFullReductionList();
        if ( list1 != null ) {
            storeProductDTO.setProductFullReductionList( new ArrayList<ProductFullReductionParam>( list1 ) );
        }
        else {
            storeProductDTO.setProductFullReductionList( null );
        }
        List<ProductLadderParam> list2 = storeProduct.getProductLadderList();
        if ( list2 != null ) {
            storeProductDTO.setProductLadderList( new ArrayList<ProductLadderParam>( list2 ) );
        }
        else {
            storeProductDTO.setProductLadderList( null );
        }
        List<ProductAttributeValueParam> list3 = storeProduct.getProductAttributeValueList();
        if ( list3 != null ) {
            storeProductDTO.setProductAttributeValueList( new ArrayList<ProductAttributeValueParam>( list3 ) );
        }
        else {
            storeProductDTO.setProductAttributeValueList( null );
        }
        List<SkuStockParam> list4 = storeProduct.getSkuStockList();
        if ( list4 != null ) {
            storeProductDTO.setSkuStockList( new ArrayList<SkuStockParam>( list4 ) );
        }
        else {
            storeProductDTO.setSkuStockList( null );
        }
        List<SubjectProductRelationParam> list5 = storeProduct.getSubjectProductRelationList();
        if ( list5 != null ) {
            storeProductDTO.setSubjectProductRelationList( new ArrayList<SubjectProductRelationParam>( list5 ) );
        }
        else {
            storeProductDTO.setSubjectProductRelationList( null );
        }
        List<PrefrenceAreaProductRelationParam> list6 = storeProduct.getPrefrenceAreaProductRelationList();
        if ( list6 != null ) {
            storeProductDTO.setPrefrenceAreaProductRelationList( new ArrayList<PrefrenceAreaProductRelationParam>( list6 ) );
        }
        else {
            storeProductDTO.setPrefrenceAreaProductRelationList( null );
        }

        return storeProductDTO;
    }

    private String storeProductStoreCategoryCateName(StoreProduct storeProduct) {
        if ( storeProduct == null ) {
            return null;
        }
        StoreCategory storeCategory = storeProduct.getStoreCategory();
        if ( storeCategory == null ) {
            return null;
        }
        String cateName = storeCategory.getCateName();
        if ( cateName == null ) {
            return null;
        }
        return cateName;
    }

    private String storeProductBrandName(StoreProduct storeProduct) {
        if ( storeProduct == null ) {
            return null;
        }
        Brand brand = storeProduct.getBrand();
        if ( brand == null ) {
            return null;
        }
        String name = brand.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long storeProductBrandId(StoreProduct storeProduct) {
        if ( storeProduct == null ) {
            return null;
        }
        Brand brand = storeProduct.getBrand();
        if ( brand == null ) {
            return null;
        }
        Long id = brand.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long storeProductStoreCategoryId(StoreProduct storeProduct) {
        if ( storeProduct == null ) {
            return null;
        }
        StoreCategory storeCategory = storeProduct.getStoreCategory();
        if ( storeCategory == null ) {
            return null;
        }
        Long id = storeCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
