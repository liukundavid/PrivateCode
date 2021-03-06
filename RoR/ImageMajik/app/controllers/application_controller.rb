class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  before_action :configure_permitted_parameters, if: :devise_controller?
  protected

  # config the devise parameter
  def configure_permitted_parameters
    devise_parameter_sanitizer.for(:sign_up) do |u|
      u.permit(:first_name, :last_name, :nick_name, :email, :password, :password_confirmation, :current_password)
    end
    devise_parameter_sanitizer.for(:account_update) do |u|
      u.permit(:first_name, :last_name, :nick_name, :email, :password, :password_confirmation, :current_password)
    end
  end
end
