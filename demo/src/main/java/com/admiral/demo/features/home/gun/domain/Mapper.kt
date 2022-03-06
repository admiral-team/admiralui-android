package com.admiral.demo.features.home.gun.domain

import com.admiral.demo.features.home.gun.domain.model.Template as TemplateDomain
import com.admiral.demo.features.home.gun.domain.model.Item as ItemDomain
import com.admiral.demo.features.home.gun.domain.model.Properties as PropertiesDomain
import com.admiral.demo.features.home.gun.data.model.Template as TemplateRemote
import com.admiral.demo.features.home.gun.data.model.Item as ItemRemote
import com.admiral.demo.features.home.gun.data.model.Properties as PropertiesRemote

fun TemplateRemote.toTemplateDomain(): TemplateDomain {
    return TemplateDomain(
        id = this.name ?: throw Exception("Template name is null"),
        description = this.description ?: throw Exception("Template description is null"),
        items = this.items?.map { it.toItemDomain() } ?: emptyList()
    )
}

fun ItemRemote.toItemDomain(): ItemDomain {
    return ItemDomain(
        id = this.id,
        description = this.description,
        properties = this.parameters.toItemDomain()
    )
}

fun PropertiesRemote.toItemDomain(): PropertiesDomain {
    return PropertiesDomain(
        isEnabled = this.enabled,
        text = this.text,
        size = this.size,
        isLoading = this.isLoading,
        isIconEnabled = this.isIconEnabled,
        additionalText = this.additionalText,
        optionalLabel = this.optionalLabel,
        state = this.state,
        titles = this.titles,
        selectedIndex = this.selectedIndex
    )
}