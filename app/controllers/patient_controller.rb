class PatientController < ApplicationController
  require 'rqrcode'
  require 'date'

  def show
    @patient = Patient.find(params[:id])
    @assessments = @patient.protocole.assessments
    today = Date.today
    @assessments.each do |a|
      unless a.protocole.date.nil?
        expected_date = a.protocole.date.to_date + a.protocol_day
        if a.realized
          if today < expected_date
            a.update(error: true)
          end
        else
          if today > expected_date
            a.update(error: true)
          end
        end
      end
    end
    @crf = @patient.protocole.assessments.select{|a| !a.crf_items.nil?}.map{|a| a.crf_items}
    render json: {status: "success", data: {patient: @patient, protocole:  @patient.protocole, clinical_study: @patient.protocole.clinical_study,
                                           assessments: @patient.protocole.assessments, crf_items:  @crf}}
  end

  def qrcode
    @qr = RQRCode::QRCode.new( '/'+params[:id]+'/index', :size => 4, :level => :h )
  end

  def create
  	@clinical_study= ClinicalStudy.all
  	@protocole_id = params[:protocole_id]
  	@site_id = current_user.site_id
  	@patient = Patient.new(:protocole_id => @protocole_id, :site_id => @site_id)
  end
end
