package com.ibrahimmakashi.socialappv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.ibrahimmakashi.socialappv2.Models.Post
import com.ibrahimmakashi.socialappv2.daos.PostDao

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var fab : FloatingActionButton
    private lateinit var adapter : PostAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var postDao: PostDao
    private lateinit var signOut : Button
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var refresh : SwipeRefreshLayout

  private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signOut = findViewById(R.id.signOut)
        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recyclerView)
        auth = Firebase.auth
        refresh = findViewById(R.id.swiperefresh)

        fab.setOnClickListener {
            val intent = Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()

        signOut.setOnClickListener {
            auth.signOut()
            val intent= Intent(this, signInActivity::class.java)
            Toast.makeText(this,"Logging Out",Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }

        refresh.setOnRefreshListener{
            refresh.isRefreshing = false

            adapter.notifyDataSetChanged()
        }

    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions,this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }


}