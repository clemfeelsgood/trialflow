class CrfItemController < ApplicationController
  def edit
  end

  def create
  	@user=current_user
  	@crf_item = Crf_item.new(:user_id => @user.email, :assessment_id => params[:id])
  	render json: {status: "success", data: @crf_item}
  end
end
