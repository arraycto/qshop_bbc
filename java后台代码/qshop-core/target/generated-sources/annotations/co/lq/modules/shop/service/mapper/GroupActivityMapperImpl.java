package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.GroupActivity;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.service.dto.GroupActivityDTO;
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
public class GroupActivityMapperImpl implements GroupActivityMapper {

    @Override
    public GroupActivity toEntity(GroupActivityDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GroupActivity groupActivity = new GroupActivity();

        groupActivity.setId( dto.getId() );
        groupActivity.setName( dto.getName() );
        groupActivity.setPrice( dto.getPrice() );
        groupActivity.setTransfee( dto.getTransfee() );
        groupActivity.setStatus( dto.getStatus() );
        groupActivity.setFeestatus( dto.getFeestatus() );
        groupActivity.setStoreId( dto.getStoreId() );
        groupActivity.setGoodsIds( dto.getGoodsIds() );
        groupActivity.setPic( dto.getPic() );
        groupActivity.setOriginprice( dto.getOriginprice() );
        groupActivity.setAddTime( dto.getAddTime() );
        groupActivity.setModifyTime( dto.getModifyTime() );
        groupActivity.setDeleted( dto.getDeleted() );
        groupActivity.setProductList( storeProductDTOListToStoreProductList( dto.getProductList() ) );

        return groupActivity;
    }

    @Override
    public GroupActivityDTO toDto(GroupActivity entity) {
        if ( entity == null ) {
            return null;
        }

        GroupActivityDTO groupActivityDTO = new GroupActivityDTO();

        groupActivityDTO.setId( entity.getId() );
        groupActivityDTO.setName( entity.getName() );
        groupActivityDTO.setPrice( entity.getPrice() );
        groupActivityDTO.setTransfee( entity.getTransfee() );
        groupActivityDTO.setStatus( entity.getStatus() );
        groupActivityDTO.setFeestatus( entity.getFeestatus() );
        groupActivityDTO.setStoreId( entity.getStoreId() );
        groupActivityDTO.setGoodsIds( entity.getGoodsIds() );
        groupActivityDTO.setPic( entity.getPic() );
        groupActivityDTO.setOriginprice( entity.getOriginprice() );
        groupActivityDTO.setAddTime( entity.getAddTime() );
        groupActivityDTO.setModifyTime( entity.getModifyTime() );
        groupActivityDTO.setDeleted( entity.getDeleted() );
        groupActivityDTO.setProductList( storeProductListToStoreProductDTOList( entity.getProductList() ) );

        return groupActivityDTO;
    }

    @Override
    public List<GroupActivity> toEntity(List<GroupActivityDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<GroupActivity> list = new ArrayList<GroupActivity>( dtoList.size() );
        for ( GroupActivityDTO groupActivityDTO : dtoList ) {
            list.add( toEntity( groupActivityDTO ) );
        }

        return list;
    }

    @Override
    public List<GroupActivityDTO> toDto(List<GroupActivity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupActivityDTO> list = new ArrayList<GroupActivityDTO>( entityList.size() );
        for ( GroupActivity groupActivity : entityList ) {
            list.add( toDto( groupActivity ) );
        }

        return list;
    }

    protected StoreProduct storeProductDTOToStoreProduct(StoreProductDTO storeProductDTO) {
        if ( storeProductDTO == null ) {
            return null;
        }

        StoreProduct storeProduct = new StoreProduct();

        storeProduct.setId( storeProductDTO.getId() );
        storeProduct.setImage( storeProductDTO.getImage() );
        storeProduct.setSliderImage( storeProductDTO.getSliderImage() );
        storeProduct.setProductName( storeProductDTO.getProductName() );
        storeProduct.setStoreInfo( storeProductDTO.getStoreInfo() );
        storeProduct.setKeyword( storeProductDTO.getKeyword() );
        storeProduct.setBarCode( storeProductDTO.getBarCode() );
        storeProduct.setStoreCateId( storeProductDTO.getStoreCateId() );
        storeProduct.setBrandId( storeProductDTO.getBrandId() );
        storeProduct.setPrice( storeProductDTO.getPrice() );
        storeProduct.setVipPrice( storeProductDTO.getVipPrice() );
        storeProduct.setOtPrice( storeProductDTO.getOtPrice() );
        storeProduct.setPostage( storeProductDTO.getPostage() );
        storeProduct.setUnitName( storeProductDTO.getUnitName() );
        storeProduct.setSort( storeProductDTO.getSort() );
        storeProduct.setSales( storeProductDTO.getSales() );
        storeProduct.setStock( storeProductDTO.getStock() );
        storeProduct.setIsShow( storeProductDTO.getIsShow() );
        storeProduct.setIsHot( storeProductDTO.getIsHot() );
        storeProduct.setIsBenefit( storeProductDTO.getIsBenefit() );
        storeProduct.setIsBest( storeProductDTO.getIsBest() );
        storeProduct.setIsNew( storeProductDTO.getIsNew() );
        storeProduct.setDescription( storeProductDTO.getDescription() );
        storeProduct.setAddTime( storeProductDTO.getAddTime() );
        storeProduct.setIsPostage( storeProductDTO.getIsPostage() );
        storeProduct.setIsDel( storeProductDTO.getIsDel() );
        storeProduct.setMerUse( storeProductDTO.getMerUse() );
        storeProduct.setGiveIntegral( storeProductDTO.getGiveIntegral() );
        storeProduct.setCost( storeProductDTO.getCost() );
        storeProduct.setFeightTemplateId( storeProductDTO.getFeightTemplateId() );
        storeProduct.setIsSeckill( storeProductDTO.getIsSeckill() );
        storeProduct.setIsBargain( storeProductDTO.getIsBargain() );
        storeProduct.setIsGood( storeProductDTO.getIsGood() );
        storeProduct.setFicti( storeProductDTO.getFicti() );
        storeProduct.setBrowse( storeProductDTO.getBrowse() );
        storeProduct.setCodePath( storeProductDTO.getCodePath() );
        storeProduct.setSoureLink( storeProductDTO.getSoureLink() );
        storeProduct.setStoreId( storeProductDTO.getStoreId() );
        storeProduct.setVerifyStatus( storeProductDTO.getVerifyStatus() );
        storeProduct.setCatalogId( storeProductDTO.getCatalogId() );
        storeProduct.setWeight( storeProductDTO.getWeight() );
        List<MemberPriceParam> list = storeProductDTO.getMemberPriceList();
        if ( list != null ) {
            storeProduct.setMemberPriceList( new ArrayList<MemberPriceParam>( list ) );
        }
        else {
            storeProduct.setMemberPriceList( null );
        }
        List<ProductFullReductionParam> list1 = storeProductDTO.getProductFullReductionList();
        if ( list1 != null ) {
            storeProduct.setProductFullReductionList( new ArrayList<ProductFullReductionParam>( list1 ) );
        }
        else {
            storeProduct.setProductFullReductionList( null );
        }
        List<ProductLadderParam> list2 = storeProductDTO.getProductLadderList();
        if ( list2 != null ) {
            storeProduct.setProductLadderList( new ArrayList<ProductLadderParam>( list2 ) );
        }
        else {
            storeProduct.setProductLadderList( null );
        }
        List<ProductAttributeValueParam> list3 = storeProductDTO.getProductAttributeValueList();
        if ( list3 != null ) {
            storeProduct.setProductAttributeValueList( new ArrayList<ProductAttributeValueParam>( list3 ) );
        }
        else {
            storeProduct.setProductAttributeValueList( null );
        }
        List<SkuStockParam> list4 = storeProductDTO.getSkuStockList();
        if ( list4 != null ) {
            storeProduct.setSkuStockList( new ArrayList<SkuStockParam>( list4 ) );
        }
        else {
            storeProduct.setSkuStockList( null );
        }
        List<SubjectProductRelationParam> list5 = storeProductDTO.getSubjectProductRelationList();
        if ( list5 != null ) {
            storeProduct.setSubjectProductRelationList( new ArrayList<SubjectProductRelationParam>( list5 ) );
        }
        else {
            storeProduct.setSubjectProductRelationList( null );
        }
        List<PrefrenceAreaProductRelationParam> list6 = storeProductDTO.getPrefrenceAreaProductRelationList();
        if ( list6 != null ) {
            storeProduct.setPrefrenceAreaProductRelationList( new ArrayList<PrefrenceAreaProductRelationParam>( list6 ) );
        }
        else {
            storeProduct.setPrefrenceAreaProductRelationList( null );
        }

        return storeProduct;
    }

    protected List<StoreProduct> storeProductDTOListToStoreProductList(List<StoreProductDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<StoreProduct> list1 = new ArrayList<StoreProduct>( list.size() );
        for ( StoreProductDTO storeProductDTO : list ) {
            list1.add( storeProductDTOToStoreProduct( storeProductDTO ) );
        }

        return list1;
    }

    protected StoreProductDTO storeProductToStoreProductDTO(StoreProduct storeProduct) {
        if ( storeProduct == null ) {
            return null;
        }

        StoreProductDTO storeProductDTO = new StoreProductDTO();

        storeProductDTO.setId( storeProduct.getId() );
        storeProductDTO.setImage( storeProduct.getImage() );
        storeProductDTO.setSliderImage( storeProduct.getSliderImage() );
        storeProductDTO.setProductName( storeProduct.getProductName() );
        storeProductDTO.setStoreInfo( storeProduct.getStoreInfo() );
        storeProductDTO.setKeyword( storeProduct.getKeyword() );
        storeProductDTO.setBarCode( storeProduct.getBarCode() );
        storeProductDTO.setStoreCateId( storeProduct.getStoreCateId() );
        storeProductDTO.setBrandId( storeProduct.getBrandId() );
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

    protected List<StoreProductDTO> storeProductListToStoreProductDTOList(List<StoreProduct> list) {
        if ( list == null ) {
            return null;
        }

        List<StoreProductDTO> list1 = new ArrayList<StoreProductDTO>( list.size() );
        for ( StoreProduct storeProduct : list ) {
            list1.add( storeProductToStoreProductDTO( storeProduct ) );
        }

        return list1;
    }
}
