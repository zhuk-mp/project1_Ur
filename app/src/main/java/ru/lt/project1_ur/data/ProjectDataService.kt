package ru.lt.project1_ur.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ProjectDataService {
    @POST("typeListKot/")
    suspend fun getTypeList(@Body request: TypeListRequest): Response<TypeListResponse>

    @POST("catalogListKot/")
    suspend fun getCatalogList(@Body request: CatalogListRequest): Response<CatalogListResponse>?


}

data class TypeListRequest(val mode: Map<String, String>)
data class TypeListResponse(val result: String, val items: List<TypeItem>)


data class TypeItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

//    @SerializedName("slug")
//    val slug: String,
//
//    @SerializedName("allow_register")
//    val allowRegister: Boolean,
//
//    @SerializedName("show_catalog")
//    val showCatalog: Boolean,
//
//    @SerializedName("show_marketplace")
//    val showMarketplace: Boolean,
//
//    @SerializedName("natural_name")
//    val naturalName: String,
//
//    @SerializedName("legal_name")
//    val legalName: String,
//
//    @SerializedName("sort")
//    val sort: Int,
//
//    @SerializedName("file_id")
//    val fileId: Int?,
//
//    @SerializedName("hide_specials")
//    val hideSpecials: Boolean,
//
//    @SerializedName("hide_detail_specials")
//    val hideDetailSpecials: Boolean,
//
//    @SerializedName("show_foreign")
//    val showForeign: Boolean,
//
//    @SerializedName("hide_back")
//    val hideBack: Boolean,
//
//    @SerializedName("show_in_check")
//    val showInCheck: Boolean,
//
//    @SerializedName("reg_number_name")
//    val regNumberName: String?,
//
//    @SerializedName("parent_id")
//    val parentId: Int?,
//
//    @SerializedName("icon_id")
//    val iconId: Int?,
//
//    @SerializedName("special_name")
//    val specialName: String,
//
//    @SerializedName("main_special_name")
//    val mainSpecialName: String,
//
//    @SerializedName("no_special_label")
//    val noSpecialLabel: String?,
//
//    @SerializedName("created_by")
//    val createdBy: String,
//
//    @SerializedName("updated_by")
//    val updatedBy: String,
//
    @SerializedName("icon")
    val icon: Icon,
//
    @SerializedName("file")
    val file: File
)


data class Icon(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("filePath")
    val filePath: String
)
data class File(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("filePath")
    val filePath: String
)

data class CatalogListRequest(
    val type_id: Int,
    val page: Int
)

data class CatalogListResponse(
    @SerializedName("result") val result: String,
    @SerializedName("content") val content: Content
) {
    data class Content(
        @SerializedName("items") val items: Items
    ) {
        data class Items(
            @SerializedName("data") val data: List<CatalogItems>
        )
    }
}

data class CatalogItems(
    @SerializedName("id") val id: Int,
//    @SerializedName("user_id") val userId: Int,
//    @SerializedName("face_id") val faceId: Int,
//    @SerializedName("type_id") val typeId: Int,
//    @SerializedName("address_id") val addressId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("desc") val description: String?,
//    @SerializedName("active") val active: Boolean,
//    @SerializedName("partner") val partner: Boolean,
//    @SerializedName("policy_agreement") val policyAgreement: Boolean,
//    @SerializedName("personal_agreement") val personalAgreement: Boolean,
//    @SerializedName("created_at") val createdAt: String,
//    @SerializedName("updated_at") val updatedAt: String,
//    @SerializedName("status") val status: String,
//    @SerializedName("stage") val stage: String,
//    @SerializedName("public") val isPublic: Boolean,
//    @SerializedName("sort") val sort: Int,
//    @SerializedName("fixed") val fixed: Int,
//    @SerializedName("fixed_all_pages") val fixedAllPages: Boolean,
    @SerializedName("views") val views: Int,
    @SerializedName("specials") val specials: List<SpecialItem>,
//    @SerializedName("mainSpecial") val mainSpecial: SpecialItem?,
//    @SerializedName("custom_url") val customUrl: String?,
//    @SerializedName("blank_window") val blankWindow: Boolean,
//    @SerializedName("in_slider") val inSlider: Boolean,
//    @SerializedName("comment") val comment: String?,
//    @SerializedName("picture_id") val pictureId: Int?,
//    @SerializedName("detected_region_id") val detectedRegionId: Int?,
//    @SerializedName("banner_id") val bannerId: Int,
//    @SerializedName("created_by") val createdBy: String,
//    @SerializedName("updated_by") val updatedBy: String,
//    @SerializedName("review_link") val reviewLink: String?,
//    @SerializedName("mobile_picture_id") val mobilePictureId: Int?,
    @SerializedName("active_reviews_count") val activeReviewsCount: Int,
//    @SerializedName("reg_nn") val regNn: String,
//    @SerializedName("form_adv") val formAdv: String,
    @SerializedName("url") val url: String,
    @SerializedName("publicAvatar") val publicAvatar: PublicAvatar,
//    @SerializedName("isExternal") val isExternal: Boolean,
    @SerializedName("public_views") val publicViews: Int,
    @SerializedName("address") val address: Address
) {
    data class SpecialItem(
//        @SerializedName("id") val id: Int,
//        @SerializedName("type_id") val typeId: Int,
        @SerializedName("title") val title: String,
//        @SerializedName("sort") val sort: Int,
//        @SerializedName("show_cabinet") val showCabinet: Boolean,
//        @SerializedName("show_catalog") val showCatalog: Boolean,
//        @SerializedName("show_marketplace") val showMarketplace: Boolean,
//        @SerializedName("created_by") val createdBy: String,
//        @SerializedName("updated_by") val updatedBy: String,
//        @SerializedName("slug") val slug: String
    )
    data class PublicAvatar(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("storage") val storage: String,
        @SerializedName("path") val path: String,
//        @SerializedName("created_at") val createdAt: String,
//        @SerializedName("updated_at") val updatedAt: String,
        @SerializedName("filePath") val filePath: String
    )

    data class Address(
        @SerializedName("id") val id: Int,
        @SerializedName("address") val address: String,
        @SerializedName("data") val data: Data
    )
    data class Data(
        @SerializedName("region_with_type") val city: String?
    )
}
