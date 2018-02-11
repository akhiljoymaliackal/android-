import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import requests
import MySQLdb
import time

from tornado.options import define, options

define("port", default="8000", help="run on the given port", type=int)
db = MySQLdb.connect("localhost","root","akhiljoy159","DDM" )
cursor = db.cursor()

class MainHandler(tornado.web.RequestHandler):
    def get(self):
            #self.write(self.request.uri)
        text=self.request.uri
        textsp=text.split("/")
        usrdet=textsp[2]
        specg=textsp[2].split("%")
        usrcmp=specg[0]
        ecg=specg[1]
        print usrdet
        print "user detail :", usrcmp
        print "ecg det",ecg
        usr=usrcmp.split(",")
        usremail=usr[0]
        usrage=usr[1]
        usrsex=usr[2]
        usrheight=usr[3]
        usrweight=usr[4]
        usrpulse=usr[5]
        usrtemp=usr[6]
        usrbp=usr[7]
        usrglu=usr[8]
        print usremail,"\n",usrage,"\n",usrsex,"\n",usrbp
        self.write(text)
        docrvw=""
        docpre=""
        docsta=""
        date=time.strftime("%y-%m-%d")

        sql = "insert into tempuser VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')" % \
 (usremail,usrage,usrsex,usrheight,usrweight,usrpulse,usrtemp,usrbp,ecg,docrvw,docpre,docsta,date,usrglu)
        cursor.execute(sql)
        cursor.execute ("""UPDATE currentuser SET emailid=%s WHERE val=%s """, (usremail,'1'))
        db.commit()

        #db.commit()
        self.write("sucess")

        #db.close()
        #test=request.GET.get('q','')
        #self.write(test)

class Doclogin(tornado.web.RequestHandler):
	def get(self):
		#val=str(0)
		#log=str(1)
		#sql = """update doclogin set flag=%s where falg=%s """,('1','1')
		#cursor.execute(sql)
		cursor.execute ("""UPDATE doconline SET flag=%s WHERE flag=%s """, ('1','0'))
        	db.commit()
class Doclogout(tornado.web.RequestHandler):
	def get(self):
		#val=str(0)
		#log=str(1)
		#sql = """update doclogin set flag=%s where falg=%s """,('1','1')
		#cursor.execute(sql)
		cursor.execute ("""UPDATE doconline SET flag=%s WHERE flag=%s """, ('0','1'))
        	db.commit()
class checkstatusdoc(tornado.web.RequestHandler):
    def get(self):
        #self.write("online")
        cursor.execute("select * from doconline")
        fetchrw=cursor.fetchone()
        if fetchrw[0][0]=='1':
            self.write("online")
        else:
            self.write("ofline")

class waitpp(tornado.web.RequestHandler):
    def get(self):
        cursor.execute ("""SELECT * FROM tempuser WHERE doctorstatus ="" """)
        NP=cursor.fetchall()
        pl=len(NP)
        self.write(str(pl))
class docassistance(tornado.web.RequestHandler):
    def get(self):
        cursor.execute ("""SELECT * FROM waitforuser """)
        totusr=cursor.fetchall()
        uinq=totusr[0][0]
        date=time.strftime("%y-%m-%d")
        if uinq!="":
            cursor.execute ("""SELECT * FROM tempuser where  emailid=%s AND visdate=%s  """, (uinq,date))
        #self.write(str(pl))
class prvreport(tornado.web.RequestHandler):
    def get(self):
        cursor.execute("""SELECT emailid from currentuser WHERE val=%s""",('1'))
        userdata=cursor.fetchone()

        #usr=ume[0][0]
        #cursor.execute("SELECT * FROM tempuser  ")
        date=time.strftime("%y-%m-%d")
        cursor.execute ("""SELECT * FROM tempuser where  emailid=%s AND visdate<%s  """, (userdata[0],date))
        prvdata=cursor.fetchall()
        #prvdata=cursor.fetchall()
        leng=len(prvdata)-1
        if leng>=0:

            data=prvdata[leng][3]+","+prvdata[leng][4]+","+prvdata[leng][5]+","+prvdata[leng][6]+","+prvdata[leng][7]+","+prvdata[leng][13]
            self.write(str(data))
        else:
            data="none"+","+"none"+","+"none"+","+"none"+","+"none"+","+"none"
            self.write(data)
def main():
    tornado.options.parse_command_line()

    application = tornado.web.Application([
        (r"/USER.*", MainHandler),(r"/DOCTOR/login",Doclogin),(r"/DOCTOR/logout",Doclogout),(r"/checkdocstatus",checkstatusdoc),(r"/prevreport",prvreport),(r"/DOCTOR/waitp",waitpp)
    ])
    http_server = tornado.httpserver.HTTPServer(application)

    http_server.listen(8000, address='192.168.1.101')

    #http_server.listen(options.port)
    tornado.ioloop.IOLoop.current().start()
    cursor.execute(sql)
if __name__ == "__main__":
	main()
