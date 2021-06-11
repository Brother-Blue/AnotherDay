package com.plannd.anotherday.collections

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.plannd.anotherday.tasks.todo.TodoTaskDAO
import java.sql.Timestamp

class TodoListDAO {
    private val TAG = "Plannd ToDo DAO"
    private val _dbURL: String = "https://anotherday-9fc28-default-rtdb.europe-west1.firebasedatabase.app/"
    private val _db: FirebaseDatabase = Firebase.database
    private val _dbRefString: String = "todo-list"
    private lateinit var _dbRef: DatabaseReference
    private lateinit var _listRef: TodoList

    // Internal todolist class as it will only be used along with the DAO
    class TodoList(title: String) {
        var title = title
        lateinit var createdOn: Timestamp
        lateinit var id: String
        lateinit var tasks: Array<TodoTaskDAO.TodoTask>

        override fun toString(): String {
            return "Title: $title\nCreated on: $createdOn\nList ID: $id\nAmount of tasks: $tasks.size"
        }
    }

    // Connect to the db using the predefined reference
    fun getReference(): DatabaseReference {
        return _db.getReference(_dbRefString)
    }

    fun createList(title: String) {
        _listRef =  TodoList(title)
        // createdOn timestamp
        getReference().setValue(_listRef)

        // Set change event listeners
        _dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val readVal = snapshot.getValue<TodoListDAO>()
                Log.d(TAG, readVal.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getList(): TodoList {
        return _listRef
    }
}