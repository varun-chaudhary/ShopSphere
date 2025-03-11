package com.example.shopsphere.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shopsphere.data.local.dao.CartDao
import com.example.shopsphere.data.local.dao.CheckoutDao
import com.example.shopsphere.data.local.dao.FavouriteDao
import com.example.shopsphere.data.local.dao.NotificationDao
import com.example.shopsphere.data.local.dao.OrderDao
import com.example.shopsphere.data.local.dao.UserLocationDao
import com.example.shopsphere.data.local.entity.Cart
import com.example.shopsphere.data.local.entity.Checkout
import com.example.shopsphere.data.local.entity.Favourite
import com.example.shopsphere.data.local.entity.Notification
import com.example.shopsphere.data.local.entity.Order
import com.example.shopsphere.data.local.entity.UserLocation

@Database(
    entities = [
        Cart::class,
        Favourite::class,
        Order::class,
        UserLocation::class,
        Checkout::class,
        Notification::class
    ],
    version = 4
)
@TypeConverters(Converter::class)
abstract class ShopSphereRoomDatabase : RoomDatabase(){
    abstract fun cartDao(): CartDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun orderDao(): OrderDao
    abstract fun checkoutDao(): CheckoutDao
    abstract fun userLocationDao(): UserLocationDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: ShopSphereRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ShopSphereRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ShopSphereRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ShopSphereRoomDatabase::class.java, "cart_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as ShopSphereRoomDatabase
        }
    }
}