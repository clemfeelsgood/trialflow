class AssessmentController < ApplicationController
  def create
    @assessment = Assessment.new(:clinical_study_id => params[:clinical_study_id])
    @post = current_user.posts.new(assessment_params)
    render json: {status: "success", data: @assessment}
  end
  
  def new
    @assessment = Assessment.new
  end
  
  def show
    
  end
end
