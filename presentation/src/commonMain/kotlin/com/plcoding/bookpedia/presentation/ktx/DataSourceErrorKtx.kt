package com.plcoding.bookpedia.presentation.ktx

import cmp_bookpedia.presentation.generated.resources.Res
import cmp_bookpedia.presentation.generated.resources.error_data_source_local_disk_full
import cmp_bookpedia.presentation.generated.resources.error_data_source_local_unknown
import cmp_bookpedia.presentation.generated.resources.error_data_source_remote_no_internet
import cmp_bookpedia.presentation.generated.resources.error_data_source_remote_serialization
import cmp_bookpedia.presentation.generated.resources.error_data_source_remote_server
import cmp_bookpedia.presentation.generated.resources.error_data_source_remote_timeout
import cmp_bookpedia.presentation.generated.resources.error_data_source_remote_too_many_requests
import cmp_bookpedia.presentation.generated.resources.error_data_source_remote_unknown
import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.presentation.UiText

fun DataSourceError.toUiText(): UiText {
    val resourceId = when (this) {
        DataSourceError.Local.DiskFull -> Res.string.error_data_source_local_disk_full
        DataSourceError.Local.Unknown -> Res.string.error_data_source_local_unknown
        DataSourceError.Remote.NoInternetConnection -> Res.string.error_data_source_remote_no_internet
        DataSourceError.Remote.TimeOut -> Res.string.error_data_source_remote_timeout
        DataSourceError.Remote.TooManyRequests -> Res.string.error_data_source_remote_too_many_requests
        DataSourceError.Remote.Server -> Res.string.error_data_source_remote_server
        DataSourceError.Remote.Serialization -> Res.string.error_data_source_remote_serialization
        DataSourceError.Remote.Unknown -> Res.string.error_data_source_remote_unknown
    }
    return UiText.StringResourceId(resourceId)
}
