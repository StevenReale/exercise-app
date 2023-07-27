import Vue from 'vue'
import Router from 'vue-router'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import store from '../store/index'

Vue.use(Router)

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'home',
            redirect: {name: "logbook"}
        },
        {
            path: '/logbook',
            name: 'Logbook',
            component: LogbookView,
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
        }
    ]
})

router.beforeEach((to, from, next) => {
    // Determine if the route requires Authentication
    const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  
    // If it does and they are not logged in, send the user to "/login"
    if (requiresAuth && store.state.token === '') {
      next("/login");
    } else {
      // Else let them go to their next destination
      next();
    }
  });