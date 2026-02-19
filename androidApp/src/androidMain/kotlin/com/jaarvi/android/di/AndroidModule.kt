package com.jaarvi.android.di

import com.jaarvi.android.capabilities.AndroidBrowserCapability
import com.jaarvi.android.capabilities.AndroidDocumentVaultCapability
import com.jaarvi.android.capabilities.AndroidMapCapability
import com.jaarvi.android.capabilities.AndroidNotificationCapability
import com.jaarvi.shared.capabilities.BrowserCapability
import com.jaarvi.shared.capabilities.DocumentVaultCapability
import com.jaarvi.shared.capabilities.MapCapability
import com.jaarvi.shared.capabilities.NotificationCapability
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for Android-specific dependencies.
 * Registers platform capability implementations.
 */
val androidModule = module {
    
    // Platform Capabilities
    single<MapCapability> { 
        AndroidMapCapability(context = androidContext())
    }
    
    single<BrowserCapability> { 
        AndroidBrowserCapability(context = androidContext())
    }
    
    single<NotificationCapability> { 
        AndroidNotificationCapability(context = androidContext())
    }
    
    single<DocumentVaultCapability> { 
        AndroidDocumentVaultCapability(context = androidContext())
    }
}
