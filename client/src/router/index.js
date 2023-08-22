import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import LogbookView from '../views/LogbookView.vue'
import EventView from '../views/EventView.vue'
import ContactView from '../views/ContactView.vue'
import store from '../store/index'

/* eslint-disable */

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: {name: "logbook"}
  },
  {
    path: '/logbook',
    name: 'logbook',
    component: LogbookView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/contact',
    name: 'contact',
    component: ContactView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/logout',
    name: 'logout',
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/workouts',
    name: 'workouts',
    redirect: {name: 'logbook'}
  },
  {
    path: '/events/:eventId',
    name: 'Event',
    component: EventView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next('/login');
  } else {
    // Else let them go to their next destination
    next();
  }

  if (to.name === 'logbook') {
    store.dispatch('updateHeaderText', 'My Logbook');
  } else if (to.name === 'Event') {
    store.dispatch('updateHeaderText', 'Edit Workout Details');
  } else if (to.name === 'login') {
    store.dispatch('updateHeaderText', 'Login');
  } else if (to.name === 'register') {
    store.dispatch('updateHeaderText', 'Register');
  } else if (to.name === 'contact') {
    store.dispatch('updateHeaderText', 'Contact');
  } else if (to.name === 'about') {
    store.dispatch('updateHeaderText', 'About');
  } else if (to.name === 'home') {
    store.dispatch('updateHeaderText', 'My Logbook');
  }
});

export default router

