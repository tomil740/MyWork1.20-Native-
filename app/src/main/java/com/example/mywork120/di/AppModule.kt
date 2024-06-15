package com.example.mywork120.di

import android.content.Context
import androidx.room.Room
import com.example.mywork120.data.local.DeclaerDatabase
import com.example.mywork120.data.repository.DeclareRepositoryImpl
import com.example.mywork120.domain.repository.DeclareRepository
import com.example.mywork120.domain.usecase.AddEditUsecase
import com.example.mywork120.domain.usecase.AllUseCase
import com.example.mywork120.domain.usecase.EntryUseCase
import com.example.mywork120.domain.usecase.GetDaySumByDate
import com.example.mywork120.domain.usecase.GetStatAndTargets
import com.example.mywork120.domain.usecase.GetSunday
import com.example.mywork120.domain.usecase.GetWeekId
import com.example.mywork120.domain.usecase.CalculateWeekSum
import com.example.mywork120.domain.usecase.GetDeclareById
import com.example.mywork120.domain.usecase.GetWeekSumFlow
import com.example.mywork120.domain.usecase.InsertWeekSum
import com.example.mywork120.domain.usecase.OnDeleteDeclareById
import com.example.mywork120.domain.usecase.OnSubmitDeclare
import com.example.mywork120.domain.usecase.OnSubmitTargets
import com.example.mywork120.domain.usecase.OnlegalityCheck
import com.example.mywork120.domain.usecase.ShouldUpdateWeekSum
import com.example.mywork120.domain.usecase.UpdateStatisticsObj
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


    //this will be the central place we define how the depnedncies are created
//and the life time will be singleton (one instance to the all App)
    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {
        @Provides
        @Singleton
        fun provideDeclaerDatabase(@ApplicationContext app: Context): DeclaerDatabase {
            return Room.databaseBuilder(
                app,
                DeclaerDatabase::class.java,
                "declare_db"
            ) //.fallbackToDestructiveMigration()
                .build()
        }


        //here we provide the repository with the data base we create here
        //digeer hill will recongize the parameter which has instance di and use it to create the
        //repository instance
        @Provides
        @Singleton
        fun provideDeclareRepository(db: DeclaerDatabase): DeclareRepository {
            return DeclareRepositoryImpl(db.declareDao)
        }

        @Provides
        @Singleton
        fun providEntryUseCase(repository: DeclareRepository): EntryUseCase {
            return EntryUseCase(
                 GetSunday(),
                 CalculateWeekSum(),
                 GetDaySumByDate(repository),
                GetWeekId(),
                ShouldUpdateWeekSum(repository),
                InsertWeekSum(repository),
                UpdateStatisticsObj(repository),
                GetStatAndTargets(repository),
                OnSubmitTargets(repository)
            )
        }

        @Provides
        @Singleton
        fun providAllUseCase(repository: DeclareRepository): AllUseCase {
            return AllUseCase(
                getSunday = GetSunday(),
              calculateWeekSum =   CalculateWeekSum(),
               getDaySumByDate =  GetDaySumByDate(repository),
               getWeekId =  GetWeekId(),
               getStatAndTargets =  GetStatAndTargets(repository),
                getWeekSumFlow = GetWeekSumFlow(repository)
            )
        }

        @Provides
        @Singleton
        fun provideAddEditUseCase(repository: DeclareRepository): AddEditUsecase {
            return AddEditUsecase(
                onSubmitDeclare = OnSubmitDeclare(repository),
                onlegalityCheck = OnlegalityCheck(),
                getDeclareById = GetDeclareById(repository = repository),
                onDeleteDeclareById = OnDeleteDeclareById(repository)
            )
        }



    }




