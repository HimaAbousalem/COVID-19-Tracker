package com.iti.mobile.covid19tracker.dagger.modules.controller
import com.iti.mobile.covid19tracker.dagger.scopes.WorkerKey
import com.iti.mobile.covid19tracker.model.sync.ChildWorkerFactory
import com.iti.mobile.covid19tracker.model.sync.SyncWork
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(SyncWork::class)
    internal abstract fun bindMyWorkerFactory(worker: SyncWork.Factory): ChildWorkerFactory
}