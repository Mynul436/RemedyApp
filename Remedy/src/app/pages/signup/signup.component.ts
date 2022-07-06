import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/userservice.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  constructor(private userService: UserService, private snack: MatSnackBar) {}
  public user = {
    firstname: '',
    lastname: '',
    username: '',
    password: '',
    email: '',
    phone: '',
  };
  ngOnInit(): void {}

  formSubmit() {
    alert('submit');

    if (this.user.firstname == '' || this.user.firstname == null) {
      // alert('FirstName  field is required!');
      this.snack.open('First Name is Required!!', 'ok', {
        duration: 3000,
      });

      return;
    }

    if (this.user.lastname == '' || this.user.lastname == null) {
      // alert('lastname  field is required!');
      this.snack.open('Last Name is Required!!', 'ok', {
        duration: 3000,
      });
      return;
    }
    if (this.user.username == '' || this.user.username == null) {
      // alert('username  field is required!');
      this.snack.open('User Name is Required!!', 'ok', {
        duration: 3000,
      });

      return;
    }
    if (this.user.password == '' || this.user.password == null) {
      // alert('password  field is required!');
      this.snack.open('Password is Required!!', 'ok', {
        duration: 3000,
      });
      return;
    }
    if (this.user.email == '' || this.user.email == null) {
      // alert('email  field is required!');
      this.snack.open('Email is Required!!', 'ok', {
        duration: 3000,
      });
      return;
    }
    if (this.user.phone == '' || this.user.phone == null) {
      // alert('phone  field is required!');
      this.snack.open('Phone number is Required!!', 'ok', {
        duration: 3000,
      });
      return;
    }
    this.userService.addUser(this.user).subscribe(
      (data:any) => {
        //success
        console.log(data);
        Swal.fire('Successfully done!!','User is Registered!! and id is '+data.id,'success')
      },
      (error) => {
        //failed
        console.log(error);
        alert('Somethin went worng!!');
      }
    );
  }
}
