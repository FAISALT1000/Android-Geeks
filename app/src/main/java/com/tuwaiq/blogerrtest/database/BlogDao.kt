package com.tuwaiq.blogerrtest.database

import androidx.lifecycle.LiveData
import com.tuwaiq.blogerrtest.database.Users.UsersInfo

interface BlogDao {

    fun addUserInfo():LiveData<List<UsersInfo>>


}