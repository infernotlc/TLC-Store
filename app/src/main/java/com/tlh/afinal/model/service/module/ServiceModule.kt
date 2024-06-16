package com.tlh.afinal.model.service.module

import com.tlh.afinal.model.service.AccountService
import com.tlh.afinal.model.service.ConfigurationService
import com.tlh.afinal.model.service.LogService
import com.tlh.afinal.model.service.impl.AccountServiceImpl
import com.tlh.afinal.model.service.impl.ConfigurationServiceImpl
import com.tlh.afinal.model.service.impl.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}