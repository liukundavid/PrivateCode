Rails.application.routes.draw do
  devise_for :users
  resources :folders

  # The priority is based upon order of creation: first created -> highest priority.
  # See how all your routes lay out with "rake routes".

  # You can have the root of your site routed with "root"
  root 'folders#welcome'

  get 'folders' => 'folders#index', as: :user_root
  get 'trash_folders' => 'folders#trash'

  get 'tags/:tag', to: 'folders#show', as: :tag

  resources :images do
    member do
      get :download
      get :delete
      get :undo_delete
      get :edit_filter
    end
    collection do
      post :create_version
    end
  end 

  #post 'create_version' => 'images#create_version'

  resources :friendship
  post 'befriend' => 'friendship#befriend'
  post 'accept_friend' => 'friendship#accept_friend'
  delete 'delete_friend' => 'friendship#delete_friend'
  delete 'decline_friend' => 'friendship#decline_friend'
  delete 'cancel_friend' => 'friendship#cancel_friend'

  #get 'befriend_friendship' => 'friendship#befriend'
  #get 'accept_friendship' => 'friendship#accept'
  #get 'decline_friendship' => 'friendship#decline'
  #get 'cancel_friendship' => 'friendship#cancel'
  #get 'delete_friendship' => 'friendship#delete'

  resources :share
  get 'sharefolder/:id' => 'share#folderpath', as: :folder_share
  get 'shareimage/:id' => 'share#imagepath', as: :image_share
  get 'sharedimages' => 'share#sharedimages', as: :sharedimages
  get 'sharedfolders' => 'share#sharedfolders', as: :sharedfolders

  post 'sharefolder' => 'share#sharefolder'
  post 'shareimage' => 'share#shareimage'
  delete 'cancelshareimage' => 'share#cancelshareimage'
  delete 'cancelsharefolder' => 'share#cancelsharefolder'
  # Example of regular route:
  #   get 'products/:id' => 'catalog#view'

  # Example of named route that can be invoked with purchase_url(id: product.id)
  #   get 'products/:id/purchase' => 'catalog#purchase', as: :purchase

  # Example resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Example resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Example resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Example resource route with more complex sub-resources:
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', on: :collection
  #     end
  #   end

  # Example resource route with concerns:
  #   concern :toggleable do
  #     post 'toggle'
  #   end
  #   resources :posts, concerns: :toggleable
  #   resources :photos, concerns: :toggleable

  # Example resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end
end
